const main = new Vue({
    el: "#main",
    data: {
        isList: true,
        isTrans: false,
        book: {
            author: {
                avatar: "../images/default-avatar.jpg",
                name: ""
            },
            cover: "",
            name: "",
            style: "",
            page: 0,
            star: 0,
            description: ""
        }
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        setBook: function (book) {
            this.book = book;
        },
        getBookId: function () {
            return this.book.id;
        },
        getBookName: function () {
            return this.book.name;
        },
        getPageList: function () {
            return this.book.pageList;
        },
        addPage: function (page) {
            this.book.pageList.push(page);
        },
        uploadModalVisible: function () {
            uploadModal.visible();
        },
        transModalVisible: function () {
            if (this.book.pageList.length > 0) {
                transModal.visible();
                this.isTrans = true;
                this.isList = false;
            }
        },
        transModalInvisible: function () {
            this.isTrans = false;
            this.isList = true;
        },
        playVideo: function (index) {
            transModal.visible(index);
        }
    },
    mounted: function () {
        let url = window.location;
        let id = getUrlParam(url, "id");
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        axios.get(requestContext + "api/books/" + id)
            .then(function (response) {
                main.setBook(response.data.data);
            }).catch(function () {
            popoverSpace.append("服务器访问失败", false);
        });
    }
});

const transModal = new Vue({
    el: "#transModal",
    data: {
        isVisible: false,
        index: 0,
        page: {},
        pageList: [],
        bookName: ""
    },
    methods: {
        visible: function (index) {
            if (index) {
                this.index = index;
            } else {
                this.index = 0;
            }
            this.bookName = main.getBookName();
            this.pageList = main.getPageList();
            if (this.pageList.length > 0) {
                this.page = this.pageList[this.index];
                this.isVisible = true;
            }
        },
        invisible: function () {
            main.transModalInvisible();
            this.pageList = null;
            this.isVisible = false;
        },
        prevTrans: function () {
            if (this.index > 0) {
                this.index--;
                this.page = this.pageList[this.index];
            }
        },
        nextTrans: function () {
            if (this.index < this.pageList.length - 1) {
                this.index++;
                this.page = this.pageList[this.index];
            }
        }
    }
});

const uploadModal = new Vue({
    el: "#uploadModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "上传",
        name: ""
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        uploadPage: function () {
            if ("" === this.name) {
                popoverSpace.append("请填写章节名称", false);
                return;
            }
            let name = this.name;
            let file = document.getElementById("file");
            if (!file) {
                popoverSpace.append("请选择章节视频", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在上传";
            Bmob.initialize("75b6a15a8791635241707418e52dcb90", "cf34d2d2b2c325fcf58079c3063526f4");
            let bmobFile = new Bmob.File(file.value, file.files[0]);
            bmobFile.save().then(function (obj) {
                axios.post(requestContext + "api/lessons", {
                    book: {id: main.getBookId()},
                    name: name,
                    image: obj.url()
                }).then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.addPage(response.data.data);
                        uploadModal.uploadCallback("添加成功", true);
                        uploadModal.invisible();
                    } else {
                        uploadModal.uploadCallback("添加失败", false);
                    }
                }).catch(function () {
                    uploadModal.uploadCallback("系统错误", false);
                });
            }, function () {
                uploadModal.uploadCallback("添加失败", false);
            });
        },
        uploadCallback: function (message, success) {
            popoverSpace.append(message, success);
            this.action = "上传";
            if (success) {
                document.getElementById("file").value = "";
            }
            this.isDisabled = false;
        }
    }
});