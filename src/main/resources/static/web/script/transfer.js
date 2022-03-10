let app = new Vue({
    el: '#app',
    data: {
        cliente: "",
        numberAccount: "",
        account: [],
        accounts: [],
        propia: "",
        form: {
            monto: "",
            description: "",
            destino: ""
        },
        transferButton: false,
        propia: false,
        tercero: false,
    },
    mounted() {
        const numberParam = window.location.search;
        console.log(numberParam);
        const urlParams = new URLSearchParams(numberParam);
        this.numberAccount = urlParams.get('account');
        console.log(this.numberAccount);

        this.cliente = sessionStorage.nombre;

        axios.get('/api/clients/current/accounts', { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(response => {
                console.log(response.data);
                response.data.forEach(account => {
                    if (account.activate == true) {
                        this.accounts.push(account)
                        console.log(account);
                    }

                });
                this.accounts.forEach(account => {
                    if (account.number == this.numberAccount) {
                        this.account = account;
                    }
                });
            })
            .catch(error => {
                console.error(error);
            });
    },
    methods: {
        selectProp() {
            prop = document.querySelector(".prop");
            terc = document.querySelector(".terc");
            this.propia = 'true';
            prop.classList.add('activate');
            terc.classList.remove('activate')
        },
        selectOther() {
            prop = document.querySelector(".prop");
            terc = document.querySelector(".terc");
            this.propia = 'false';
            this.form.destino = "";
            this.form.monto = "";
            this.form.description = "";
            terc.classList.add('activate')
            prop.classList.remove('activate')

        },
        transfer() {
            axios.post('/api/transactions', `amount=${this.form.monto}&description=${this.form.description}&numberAccount=${this.numberAccount}&numberAccountDestiny=${this.form.destino}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    setTimeout(function() {
                        window.location.href = "/web/accounts.html";
                    }, 3500)

                })
                .catch(error => {
                    console.log(error.response.data);
                })
        },
        signout() {
            console.log("salir");
            axios.post('/api/logout').then(response => {
                    console.log('signed out!!!');
                    window.location.href = "/web/index.html";
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
})
console.log("transferencia");
window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})