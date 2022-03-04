let app = new Vue({
    el: '#app',
    data: {
        user: "",
        password: ""
    },
    methods: {
        login() {
            axios.post('/api/login', `email=${this.user}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log("entro");
                    window.location.href = "/web/admin-loans.html";
                })
                .catch(error => {
                    console.log(error.response.data);
                    this.error = true;
                    this.user = "";
                    this.password = "";
                })
        },
    },
})