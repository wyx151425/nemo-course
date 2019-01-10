const main = new Vue({
    el: "#main",
    data: {
        tabIndex: 0,
        user: {
            avatar: "../images/default.png",
            name: "",
            motto: "",
            book: 0,
            follow: 0,
            follower: 0,
            favorite: 0
        },
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        addBook: function (book) {
            this.user.bookList.push(book);
        },
        changeTab: function (tabIndex) {
            this.tabIndex = tabIndex;
        },
        bookCreateModalVisible: function () {
            bookCreateForm.visible();
        }
    },
    mounted: function () {
        axios.get(requestContext + "api/users/current")
            .then(function (response) {
                main.setUser(response.data.data);
            }).catch(function () {
            popoverSpace.append("服务器访问失败", false);
        });
    }
});