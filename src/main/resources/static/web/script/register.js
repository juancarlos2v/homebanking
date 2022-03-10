let app = new Vue({
    el: '#app',
    data: {
        registro: {
            firstName: "",
            lastName: "",
            email: "",
            password: ""
        },
        accountType: "",
        incompleto: false,
        modalAccount: false
    },
    methods: {
        login() {
            axios.post('/api/login', `email=${this.registro.email}&password=${this.registro.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log(error)
                })
        },
        resgistrationAccount() {
            this.modalAccount = true;
            console.log("entro");
        },
        close() {
            this.modalAccount = false;
        },
        signup() {
            axios.post('/api/clients', `firstName=${this.registro.firstName}&lastName=${this.registro.lastName}&email=${this.registro.email}&password=${this.registro.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('registered');
                    this.login();
                })
                .catch(error => {
                    console.log(error)
                    this.incompleto = true;
                    // this.registro.firstName = "";
                    // this.registro.lastName = "";
                    // this.registro.email = "";
                    // this.registro.password = "";
                });
            axios.post('/api/clients/current/accounts', `accountType=${this.accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('cuenta creada');
                })
                .catch(error => {
                    console.log(error)
                })

            window.location.href = "/web/accounts.html";
        },
    }
})



window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
});