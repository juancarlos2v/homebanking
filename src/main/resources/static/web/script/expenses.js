const idParam = window.location.search;
console.log(idParam);
const urlParams = new URLSearchParams(idParam);
var number = urlParams.get('number');
console.log(number);

let app = new Vue({
    el: '#app',
    data: {
        cliente: "",
        tarjeta: [],
        consumos: []

    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                let tarjetas = response.data.cards;
                tarjetas.forEach(tarjeta => {
                    if (tarjeta.number == number) { this.tarjeta = tarjeta; }
                });
                console.log(this.tarjeta);
                this.consumos = this.tarjeta.expenses;
                this.consumos.sort((a, b) => b.id - a.id);
                this.cliente = sessionStorage.getItem('nombre');
            })
            .catch(error => {
                console.log(error);
            })
    },
    methods: {
        signout() {
            console.log("salir");
            axios.post('/api/logout').then(response => {
                    window.location.href = "/web/index.html";
                })
                .catch(error => {
                    console.log(error)
                })
        },
    },
})

window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})