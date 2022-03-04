let app = new Vue({
    el: '#app',
    data: {
        cliente: "",
        cards: [],
        services: [{
            id: 1,
            name: "EDESUR",
        }, {
            id: 2,
            name: "METROGAS"
        }, {
            id: 3,
            name: "AYSA"
        }],
        payment: {
            cardNumber: 0,
            cvv: "",
            amount: "",
            description: ""
        },
        formPago: false,
        newService: null,
        numberC: "",
        cargando: false,
        modalPago: false,
        modalError: false,
        messageError: ""
    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                this.cards = response.data.cards;
                console.log(this.cards);
                bod = document.querySelector(".contenedor-total");
            })
            .catch(error => {
                console.log(error.response.data)
            });
        this.cliente = sessionStorage.getItem('nombre');
    },
    methods: {
        signout() {
            console.log("salir");
            axios.post('/api/logout').then(response => {
                    window.location.href = "/web/index.html";
                })
                .catch(error => {
                    console.log(error.response.data)
                })
        },
        paymentElection(service) {
            this.payment.description = service;
            this.formPago = true;
            this.newService = false;
        },
        requestPay() {
            this.modalPago = true;
            bod.classList.add('desenfocar');
        },
        close() {
            this.modalError = false;
            this.modalPago = false;
            bod.classList.remove('desenfocar');
        },
        pay() {
            this.cargando = true;
            this.modalPago = false;
            axios.post('/api/payments', { numberCard: this.payment.cardNumber, cvv: this.payment.cvv, amount: this.payment.amount, description: this.payment.description })
                .then(response => {
                    this.cargando = false;
                    console.log('pago');
                    window.location.href = "/web/accounts.html"
                })
                .catch(error => {
                    console.log(error.response.data);
                    this.modalError = true;
                    this.cargando = false;
                    this.messageError = error.response.data;
                    bod.classList.add('desenfocar');
                    this.messageError.data = error.response.data;
                })
        },
        addService() {
            this.payment.description = "";
            this.newService = true;
            this.formPago = true;

        }

    },
})

window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})