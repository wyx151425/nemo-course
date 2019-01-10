let requestContext = "http://localhost:8090/";

function getUrlParam(url, name) {
    let pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    let matcher = pattern.exec(url);
    let items = null;
    if (null !== matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch (e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch (e) {
                items = matcher[1];
            }
        }
    }
    return items;
}

function getFileNativeUrl(file) {
    let url = null;
    if (undefined !== window.createObjectURL) {
        url = window.createObjectURL(file);
    } else if (undefined !== window.URL) {
        url = window.URL.createObjectURL(file);
    } else if (undefined !== window.webkitURL) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function getMessage(statusCode) {
    switch (statusCode) {
        case 300:
            return "系统状态错误";
        case 500:
            return "系统运行错误";
        case 600:
            return "请求参数错误";
        case 1000:
            return "用户未注册";
        case 1001:
            return "用户已注册";
        case 1002:
            return "用户被禁用";
        case 1003:
            return "登录超时";
        case 1004:
            return "密码错误";
    }
}

const header = new Vue({
    el: "#header",
    data: {
        defaultImg: "this.src='../images/default.png'",
        isVisible: false,
        timer: null,
        user: {}
    },
    methods: {
        visible: function () {
            clearInterval(this.timer);
            this.timer = null;
            this.isVisible = true;
        },
        invisible: function () {
            this.timer = setInterval(function () {
                header.isVisible = false;
            }, 500);
        },
        loginModalVisible: function () {
            loginForm.visible();
        },
        bookCreateModalVisible: function () {
            bookCreateForm.visible();
        },
        logout: function () {
            axios.post(requestContext + "/api/users/logout")
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode || 1003 === statusCode) {
                        localStorage.removeItem("user");
                        window.location.href = requestContext + "index";
                    }
                }).catch(function () {
                popoverSpace.append("服务器访问失败", false);
            });
        }
    },
    mounted: function () {
        this.user = JSON.parse(localStorage.getItem("user"));
    }
});

const bookCreateForm = new Vue({
    el: "#bookCreateForm",
    data: {
        isVisible: false,
        book: {
            name: "",
            style: "请选择",
            type: "请选择",
            description: "",
            cover: "../images/default.png"
        },
        typeArray: [
            {style: "高三", typeList: ["语文", "数学", "英语", "物理", "化学", "生物", "政治", "历史", "地理"]},
            {style: "高二", typeList: ["语文", "数学", "英语", "物理", "化学", "生物", "政治", "历史", "地理"]},
            {style: "高一", typeList: ["语文", "数学", "英语", "物理", "化学", "生物", "政治", "历史", "地理"]},
            {style: "初三", typeList: ["语文", "数学", "英语", "物理", "化学", "政治", "历史"]},
            {style: "初二", typeList: ["语文", "数学", "英语", "物理", "政治", "历史", "地理", "生物"]},
            {style: "初一", typeList: ["语文", "数学", "英语", "政治", "历史", "地理", "生物"]},
            {style: "六年级", typeList: ["语文", "数学", "英语"]},
            {style: "五年级", typeList: ["语文", "数学", "英语"]},
            {style: "四年级", typeList: ["语文", "数学", "英语"]},
            {style: "三年级", typeList: ["语文", "数学", "英语"]},
            {style: "二年级", typeList: ["语文", "数学", "英语"]},
            {style: "一年级", typeList: ["语文", "数学", "英语"]},
        ],
        typeList: [],
        oldURL: "",
        isChosen: false,
        action: "创建",
        isDisabled: false,
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        selectType: function () {
            for (let index = 0; index < this.typeArray.length; index++) {
                if (this.book.style === this.typeArray[index].style) {
                    this.typeList = this.typeArray[index].typeList;
                    break;
                }
            }
        },
        chooseCover: function () {
            document.getElementById("file").click();
        },
        changeCover: function () {
            this.isChosen = true;
            this.oldURL = this.book.cover;
            this.book.cover = getFileNativeUrl(document.getElementById("file").files[0]);
        },
        cancelChoice: function () {
            this.isChosen = false;
            this.book.cover = this.oldURL;
            this.oldURL = "";
            document.getElementById("file").value = "";
        },
        createBook: function () {
            if (!document.getElementById("file").files[0]) {
                popoverSpace.append("请选择课程封面", false);
                return;
            }
            if ("" === this.book.name) {
                popoverSpace.append("请填写课程名称", false);
                return;
            }
            if ("请选择" === this.book.style) {
                popoverSpace.append("请选择课程所属年级", false);
                return;
            }
            if ("请选择" === this.book.type) {
                popoverSpace.append("请选择课程所属学科", false);
                return;
            }
            if ("" === this.book.description) {
                popoverSpace.append("请填写课程简介", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在创建";
            Bmob.initialize("75b6a15a8791635241707418e52dcb90", "cf34d2d2b2c325fcf58079c3063526f4");
            let file = document.getElementById("file");
            let bmobFile = new Bmob.File(file.value, file.files[0]);
            bmobFile.save().then(function (obj) {
                bookCreateForm.book.cover = obj.url();
                axios.post(requestContext + "api/books", bookCreateForm.book)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            bookCreateForm.createCallback("创建成功", true, response.data.data);
                        } else {
                            bookCreateForm.createCallback("创建失败", false);
                        }
                    }).catch(function () {
                    bookCreateForm.createCallback("系统错误", false);
                });
            }, function () {
                bookCreateForm.createCallback("创建失败", false);
            });
        },
        createCallback: function (message, isSuccess, book) {
            popoverSpace.append(message, isSuccess);
            this.action = "创建";
            if (isSuccess) {
                this.book.cover = "../images/default.png";
                this.book.name = "";
                this.book.type = "请选择";
                this.book.style = "请选择";
                this.book.description = "";
                this.isChosen = false;
                document.getElementById("file").value = "";
            }
            this.isDisabled = false;
            if (this.isVisible) {
                main.addBook(book);
                this.isVisible = false;
            }
        }
    }
});

Vue.component("popover", {
    props: ["prompt"],
    template: `
            <div class="popover" :class="{success: prompt.success, error: !prompt.success}">
                <span class="icon icon-ok" v-if="prompt.success"></span>
                <span class="icon icon-remove" v-if="!prompt.success"></span>
                <span>{{prompt.message}}</span>
            </div>
        `
});

const popoverSpace = new Vue({
    el: "#popoverSpace",
    data: {
        prompts: [],
        index: 0
    },
    methods: {
        append: function (message, success) {
            let prompt = {id: this.index, success: success, message: message};
            this.index++;
            this.prompts.push(prompt);
            setTimeout(function () {
                popoverSpace.prompts.shift(prompt);
            }, 5000);
        }
    }
});