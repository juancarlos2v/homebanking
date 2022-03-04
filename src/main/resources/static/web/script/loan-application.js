let app = new Vue({
    el: '#app',
    data: {
        idPrestamo: 0,
        cuentas: [],
        prestamos: [],
        loan: {},
        payments: 0,
        monto: "",
        numero: "",
        showAll: true,
        form: false,
        mod: false,
        pago: 0,
        cuota: 0,
        errorRequest: false,
        modError: false,
        messageError: ""

    },
    mounted() {
        axios.get('/api/loans')
            .then(response => {
                this.prestamos = response.data;
            })
            .catch(error => {
                console.log(error);
            });
        axios.get('/api/clients/current/accounts')
            .then(response => {
                this.cuentas = response.data;
            })
            .catch(error => {
                console.log(error);
            });

    },
    methods: {
        all() {
            this.showAll = true;
            this.form = false;
            this.idPrestamo = 0;
            this.loan = [];
        },
        election(id) {
            this.idPrestamo = id;
            console.log(this.idPrestamo);
            this.showAll = false;
            this.prestamos.forEach(prestamo => {
                if (prestamo.id == id) {
                    this.form = true;
                    this.loan = prestamo;
                }
            });
            console.log(this.loan);
            console.log(this.form);
        },
        request() {
            if (this.monto == 0 || this.payments == 0 || this.numero == "") {
                this.errorRequest = true;

            } else {
                console.log(this.loan.rate);
                let bod = document.querySelector(".contenedor-total");
                bod.classList.add('desenfocar');
                let porcentaje = parseFloat(this.loan.rate / 100);
                this.pago = parseFloat(this.monto) + parseFloat(this.monto) * porcentaje;
                console.log(this.pago)
                this.cuota = parseFloat(this.pago / this.payments).toFixed(2);
                this.mod = true;
            }
        },
        close() {
            var bod = document.querySelector(".contenedor-total");
            bod.classList.remove('desenfocar');
            this.mod = false;
            this.modError = false;
        },
        loanSubmit() {
            axios.post('/api/loans', { id: this.idPrestamo, amount: this.monto, payments: this.payments, account: this.numero, amountFinal: this.pago })
                .then(response => {
                    console.log('prestamo aprobado');
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log(error.response);
                    this.modError = true;
                    this.mod = false;
                    this.messageError = error.response.data;

                })
        },
        signout() {
            console.log("salir");
            axios.post('/api/logout').then(response => {
                    console.log('signed out');
                    window.location.href = "/web/index.html";
                })
                .catch(error => {
                    console.log(error)
                })
        }
    },
})

window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})