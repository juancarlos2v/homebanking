let app = new Vue({
    el: '#app',
    data: {
        registro: {
            firstName: "",
            lastName: "",
            email: "",
            password: ""
        },
        incompleto: false
    },
    methods: {
        login() {
            //console.log(this.registro);
            axios.post('/api/login', `email=${this.registro.email}&password=${this.registro.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('signed in!!!');
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log(error)
                })
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
                    this.registro.firstName = "";
                    this.registro.lastName = "";
                    this.registro.email = "";
                    this.registro.password = "";
                });
        }
    }
})



window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
});