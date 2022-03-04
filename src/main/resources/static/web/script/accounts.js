let app = new Vue({
    el: '#app',
    data: {
        cliente: [],
        cuentas: [],
        prestamos: [],
        cuentasActivas: 0,
        accountType: "",
        accountDelete: [],
        modalCuenta: false,
        modalBaja: false,
        cuentaDestino: "",
        name: "",
    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                this.cliente = response.data;
                this.cuentas = this.cliente.accounts;
                this.cuentas.sort((a, b) => b.id - a.id);
                this.cuentas.forEach(cuenta => {
                    if (cuenta.activate) this.cuentasActivas += 1;
                });
                this.prestamos = this.cliente.prestamos;
                this.name = this.cliente.firstName;
                console.log(this.cliente);
                console.log(this.name);
                sessionStorage.setItem('nombre', this.name);
            })
            .catch(error => {
                console.log(error)
            });


    },
    methods: {
        requestAccount() {
            this.modalCuenta = true;
            var bod = document.querySelector(".contenedor-total");
            bod.classList.add('desenfocar');


        },
        requestUnubscribe(account) {
            this.modalBaja = true;
            this.accountDelete = account;
            var bod = document.querySelector(".contenedor-total");
            bod.classList.add('desenfocar');

        },
        close() {
            this.cuentaDestino = "";
            this.modalCuenta = false;
            this.modalBaja = false;
            var bod = document.querySelector(".contenedor-total");
            bod.classList.remove('desenfocar');
        },
        newAccount() {
            axios.post('/api/clients/current/accounts', `accountType=${this.accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('cuenta creada');
                    window.location.reload();
                })
                .catch(error => {
                    console.log(error)
                })
        },
        deleteAccount(number) {
            axios.patch('/api/accounts/', `number=${number}&bool=false`)
                .then(response => {
                    console.log("Cuenta eliminada");
                    window.location.href = "/web/accounts.html";
                })
        },
        transfer() {
            axios.post('/api/transactions', `amount=${this.accountDelete.balance}&description="transferencia por baja"&numberAccount=${this.accountDelete.number}&numberAccountDestiny=${this.cuentaDestino}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('tranf');
                    this.accountDelete.balance = 0;
                    //window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log(error);
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
});
window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})