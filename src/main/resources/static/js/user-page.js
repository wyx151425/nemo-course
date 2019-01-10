const main = new Vue({
    el: "#main",
    data: {
        page: {
            book: {
                cover: "../images/default.png"
            },
            image: "../images/default.png"
        }
    },
    methods: {
        getBookId: function () {
            return this.page.book.id;
        },
        setPage: function (page) {
            this.page = page;
        },
        getPage: function () {
            return this.page;

        },
        getPageId() {
            return this.page.id;
        },
        setPageImage: function (image) {
            this.page.image = image;
        },
        updateModalVisible: function () {
            updateModal.visible();
        },
        deleteModalVisible: function () {
            deleteModal.visible();
        }
    },
    mounted: function () {
        let url = window.location;
        let id = getUrlParam(url, "id");
        axios.get(requestContext + "api/pages/" + id)
            .then(function (response) {
                main.setPage(response.data.data);
            })
            .catch(function () {
                popoverSpace.append("服务器访问失败", false);
            });
    }
});

const updateModal = new Vue({
    el: "#updateModal",
    data: {
        image: "",
        action: "更新",
        isVisible: false,
        isChosen: false,
        isDisabled: false
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.image = "";
            this.isChosen = false;
            this.isVisible = false;
        },
        choosePage: function () {
            document.getElementById("file").click();
        },
        analyzePage: function () {
            this.isChosen = true;
            this.image = getFileNativeUrl(document.getElementById("file").files[0]);
        },
        cancelChoice: function () {
            this.image = "";
            document.getElementById("file").value = "";
            this.isChosen = false;
        },
        updatePage: function () {
            this.isDisabled = true;
            this.action = "正在更新";
            Bmob.initialize("75b6a15a8791635241707418e52dcb90", "cf34d2d2b2c325fcf58079c3063526f4");
            let file = document.getElementById("file");
            let bmobFile = new Bmob.File(file.value, file.files[0]);
            bmobFile.save().then(
                function (obj) {
                    let page = main.getPage();
                    page.image = obj.url();
                    axios.put(requestContext + "api/pages", page)
                        .then(function (response) {
                            let statusCode = response.data.statusCode;
                            if (200 === statusCode) {
                                main.setPageImage(obj.url());
                                updateModal.updateResult("更新成功", true);
                            } else {
                                updateModal.updateResult("更新失败", false);
                            }
                        })
                        .catch(function () {
                            updateModal.updateResult("服务器访问失败", false);
                        });
                },
                function () {
                    updateModal.updateResult("更新失败", false);
                });
        },
        updateResult: function (message, isSuccess) {
            popoverSpace.append(message, isSuccess);
            this.action = "更新";
            this.isDisabled = false;
            if (isSuccess) {
                this.invisible();
            }
        }
    }
});

const deleteModal = new Vue({
    el: "#deleteModal",
    data: {
        isVisible: false,
        deleting: false,
        deletePrompt: ""
    },
    methods: {
        visible: function () {
            this.deletePrompt = "您确定要删除该漫画分页吗？";
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        deletePage: function () {
            this.deleting = true;
            this.deletePrompt = "正在删除...";
            let url = requestContext + "api/pages/" + main.getPageId();
            axios.delete(url)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        window.location.href = requestContext + "user/book?id=" + main.getBookId();
                    } else {
                        deleteModal.deleteResult("删除失败", false);
                    }
                }).catch(function () {
                deleteModal.deleteResult("系统错误", false);
            });
        },
        deleteResult: function (message, success) {
            popoverSpace.append(message, success);
            this.deleting = false;
            if (success) {
                this.isVisible = false;
            }
        }
    }
});