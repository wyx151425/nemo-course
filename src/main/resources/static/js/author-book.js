const main = new Vue({
    el: "#main",
    data: {
        isList: true,
        isTrans: false,
        book: {
            author: {
                avatar: "../images/default.png",
                name: ""
            },
            cover: "../images/default.png",
            name: "",
            style: "",
            page: 0,
            star: 0,
            description: ""
        },
        favorite: null,
        isFavorite: false,
        isDisabled: false,
        user: {}
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
        favoriteBook: function () {
            this.isDisabled = true;
            if (!this.isFavorite) {
                axios.post(requestContext + "api/favorites", {
                    book: this.book,
                    user: this.user
                }).then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.favoriteCallback(response.data.data);
                        popoverSpace.append("收藏成功", true);
                    } else {
                        popoverSpace.append("收藏失败", false);
                    }
                }).catch(function () {
                    popoverSpace.append("服务器访问失败", false);
                });
            } else {
                axios.delete(requestContext + "api/favorites/" + this.favorite.id)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            main.disfavorCallback();
                            popoverSpace.append("取消成功", true);
                        } else {
                            popoverSpace.append("取消失败", false);
                        }
                    })
                    .catch(function () {
                        popoverSpace.append("服务器访问失败", false);
                    });
            }
        },
        favoriteCallback: function (favorite) {
            this.favorite = favorite;
            this.isFavorite = true;
            this.isDisabled = false;
        },
        disfavorCallback: function () {
            this.favorite = null;
            this.isFavorite = false;
            this.isDisabled = false;
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
                axios.get(requestContext + "api/favorites?bookId=" + id + "&userId=" + user.id)
                    .then(function (response) {
                        if (null !== response.data.data) {
                            main.favoriteCallback(response.data.data);
                        }
                    });
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
        prevUrl: "",
        nextUrl: "",
        pageList: [],
        bookName: ""
    },
    methods: {
        visible: function () {
            this.bookName = main.getBookName();
            this.pageList = main.getPageList();
            if (this.pageList.length > 0) {
                this.index = 0;
                this.page = this.pageList[this.index];
                if (this.pageList.length > 1) {
                    this.nextUrl = this.pageList[this.index + 1].image;
                }
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
                this.nextUrl = this.page.image;
                this.index--;
                this.page = this.pageList[this.index];
                if (this.index > 0) {
                    this.prevUrl = this.pageList[this.index - 1].image;
                } else {
                    this.prevUrl = "";
                }
            }
        },
        nextTrans: function () {
            if (this.index < this.pageList.length - 1) {
                this.prevUrl = this.page.image;
                this.index++;
                this.page = this.pageList[this.index];
                if (this.index < this.pageList.length - 1) {
                    this.nextUrl = this.pageList[this.index + 1].image;
                } else {
                    this.nextUrl = "";
                }
            }
        }
    }
});

const uploadModal = new Vue({
    el: "#uploadModal",
    data: {
        image: "",
        isChosen: false,
        isVisible: false,
        action: "上传",
        isDisabled: false
    },
    methods: {
        visible: function () {
            this.isChosen = false;
            this.isVisible = true;
        },
        invisible: function () {
            this.image = "";
            this.isVisible = false;
        },
        choosePage: function () {
            document.getElementById("file").click();
        },
        changePage: function () {
            this.isChosen = true;
            this.image = getFileNativeUrl(document.getElementById("file").files[0]);
        },
        cancelChoice: function () {
            this.isChosen = false;
            document.getElementById("file").value = "";
        },
        uploadPage: function () {
            this.isDisabled = true;
            this.action = "正在上传";
            Bmob.initialize("75b6a15a8791635241707418e52dcb90", "cf34d2d2b2c325fcf58079c3063526f4");
            let file = document.getElementById("file");
            let bmobFile = new Bmob.File(file.value, file.files[0]);
            bmobFile.save().then(function (obj) {
                axios.post(requestContext + "api/pages", {
                    book: {id: main.getBookId()},
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
        uploadCallback: function (message, isSuccess) {
            popoverSpace.append(message, isSuccess);
            this.action = "上传";
            if (isSuccess) {
                this.image = "";
                this.oldUrl = "";
                this.isChosen = false;
            }
            this.isDisabled = false;
        }
    }
});