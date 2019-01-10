const main = new Vue({
    el: "#main",
    data: {
        bookList: []
    },
    methods: {
        setBookList: function (bookList) {
            this.bookList = bookList;
        }
    },
    mounted: function () {
        axios.get(requestContext + "api/books")
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setBookList(response.data.data);
                } else {
                    popoverSpace.append("漫画册获取失败", false);
                }
            })
            .catch(function () {
                popoverSpace.append("服务器访问失败", false);
            });
    }
});