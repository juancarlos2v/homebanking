let app = new Vue({
    el: '#app',
    data: {
        client: "user",
        tarjetas: [],
        cuentas: [],
        form: {
            type: "",
            color: "",

        },
        dataAssociate: {
            cardNumber: "",
            accountNumber: ""
        },
        modalError: false,
        messageError: "",
        cardCreate: false,
        newAssociate: false,
        confirmation: false,
        cardConfirmation: false

    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                this.cliente = response.data;
                this.cuentas = response.data.accounts;
                this.tarjetas = response.data.cards;
                console.log(this.tarjetas);
            })
            .catch(error => {
                console.log(error.response.data);


            });
        bod = document.querySelector(".contenedor-total");
        creditButton = document.querySelector(".credit");
        debitButton = document.querySelector(".debit");
    },
    methods: {
        newCard() {
            axios.post('/api/clients/current/cards', `cardType=${this.form.type}&cardColor=${this.form.color}`)
                .then(response => {
                    console.log("Tarjeta creada");
                    console.log(response.data);
                    if (this.form.type == "CREDIT") {
                        bod.classList.add('desenfocar');
                        this.cardConfirmation = true;
                        setTimeout(function() {
                            window.location.href = "/web/cards.html"
                        }, 3500);
                    } else {
                        bod.classList.add('desenfocar');
                        this.dataAssociate.cardNumber = response.data;
                        this.cardCreate = true;
                    }


                })
                .catch(error => {
                    console.error(error.response.data);
                    this.messageError = error.response.data;
                    this.modalError = true;
                    bod.classList.add('desenfocar');
                })
        },
        requestDebit() {
            this.form.type = "DEBIT"
            debitButton.classList.add('activate');
            creditButton.classList.remove('activate')
        },
        requestCredit() {
            this.form.type = "CREDIT"
            creditButton.classList.add('activate');
            debitButton.classList.remove('activate');

        },
        colorClick(color) {
            g = document.querySelector(".background-gold");
            s = document.querySelector(".background-silver");
            t = document.querySelector(".background-titanium");
            this.form.color = color;
            if (color == 'GOLD') {
                g.classList.add('border');
                s.classList.remove('border');
                t.classList.remove('border');
            }
            if (color == 'SILVER') {
                g.classList.remove('border');
                s.classList.add('border');
                t.classList.remove('border');
            }
            if (color == 'TITANIUM') {
                g.classList.remove('border');
                s.classList.remove('border');
                t.classList.add('border');
            }
        },
        goAsocciate() {
            this.cardCreate = false;
            this.newAssociate = true;
        },
        associateCard() {
            axios.post('/api/card/associate', `cardNumber=${this.dataAssociate.cardNumber}&&accountNumber=${this.dataAssociate.accountNumber}`)
                .then(response => {
                    this.confirmation = true;
                    setTimeout(function() {
                        window.location.href = "/web/cards.html"
                    }, 3500);

                })
        },
        back() {
            this.cardConfirmation = true;
            this.cardCreate = false;
            this.form.type = "";
            setTimeout(function() {
                window.location.href = "/web/cards.html";
            }, 3500)

        },
        close() {
            this.modalError = false;
            var bod = document.querySelector(".contenedor-total");
            bod.classList.remove('desenfocar');
        },
        signout() {
            console.log("salir");
            axios.post('/api/logout').then(response => {
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