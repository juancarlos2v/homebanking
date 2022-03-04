let app = new Vue({
    el: '#app',
    data: {
        client: "user",
        tarjetas: [],
        form: {
            type: "",
            color: "",

        },
        modalError: false,
        messageError: "",
    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                this.cliente = response.data;
                this.tarjetas = response.data.cards;
                console.log(this.tarjetas);
            })
            .catch(error => {
                console.log(error.response.data);


            });
        creditButton = document.querySelector(".credit");
        debitButton = document.querySelector(".debit");
    },
    methods: {
        newCard() {
            axios.post('/api/clients/current/cards', `cardType=${this.form.type}&cardColor=${this.form.color}`)
                .then(response => {
                    console.log("Tarjeta creada");
                    window.location.href = "/web/cards.html";
                })
                .catch(error => {
                    console.error(error.response.data);
                    this.messageError = error.response.data;
                    this.modalError = true;
                    var bod = document.querySelector(".contenedor-total");
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
        close() {
            this.modalError = false;
            var bod = document.querySelector(".contenedor-total");
            bod.classList.remove('desenfocar');
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