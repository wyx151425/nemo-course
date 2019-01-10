const main = new Vue({
    el: "#main",
    data: {
        user: {},
        author: {},
        follow: null,
        action: "关注",
        isFollowed: false,
        isDisabled: false
    },
    methods: {
        setAuthor: function (author) {
            this.author = author;
        },
        followAuthor: function () {
            this.isDisabled = true;
            if (!this.isFollowed) {
                axios.post(requestContext + "api/follows", {
                    author: this.author,
                    user: this.user
                }).then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.followCallback(response.data.data);
                        popoverSpace.append("关注成功", true);
                    } else {
                        popoverSpace.append("关注失败", false);
                    }
                }).catch(function () {
                    popoverSpace.append("服务器访问失败", false);
                });
            } else {
                axios.delete(requestContext + "api/follows/" + this.follow.id)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            main.unfollowCallback();
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
        followCallback: function (follow) {
            this.follow = follow;
            this.isFollowed = true;
            this.action = "已关注";
            this.isDisabled = false;
        },
        unfollowCallback: function () {
            this.follow = null;
            this.isFollowed = false;
            this.action = "关注";
            this.isDisabled = false;
        }
    },
    mounted: function () {
        let url = window.location;
        let id = getUrlParam(url, "id");
        let user = JSON.parse(localStorage.getItem("user"));
        this.user = user;
        axios.get(requestContext + "api/authors/" + id)
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setAuthor(response.data.data);
                    axios.get(requestContext + "api/follows?authorId=" + id + "&userId=" + user.id)
                        .then(function (response) {
                            if (null !== response.data.data) {
                                main.followCallback(response.data.data);
                            }
                        });
                } else {
                    popoverSpace.append("漫画家获取失败", false);
                }
            }).catch(function () {
            popoverSpace.append("服务器访问失败", false);
        });
    }
});