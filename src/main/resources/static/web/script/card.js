let app = new Vue({
    el: '#app',
    data: {
        cliente: [],
        tarjetas: [],
        numberCard: "",
    },
    mounted() {
        axios.get('/api/clients/current')
            .then(response => {
                this.cliente = response.data;
                this.tarjetas = response.data.cards;
                this.tarjetas.sort((a, b) => b.id - a.id);
                console.log(this.tarjetas);
                var date = new Date();
                let d = `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`;
                var data = new Date(d);
                console.log(data.getTime());
                let i = 0;
                this.tarjetas.forEach(tarjeta => {
                    // console.log(tarjeta.thruDate);
                    if (new Date(tarjeta.thruDate).getTime() > data) {
                        //console.log("Vigente");
                        this.tarjetas[i].vigencia = true;
                    } else {
                        this.tarjetas[i].vigencia = false;
                    }
                    console.log('format date');
                    //let d = tarjeta.thruDate.replace(/^(\d{4})-(\d{2})-(\d{2})$/g, '$3/$2/$1')
                    //console.log(d)
                    let d = new Date(tarjeta.thruDate + 'T00:00:00');
                    let h = new Date(tarjeta.fromDate + 'T00:00:00');
                    // console.log(d);
                    this.tarjetas[i].desde = d.getMonth() + 1 + "/" + (d.getFullYear() - 2000);
                    this.tarjetas[i].hasta = h.getMonth() + 1 + "/" + (h.getFullYear() - 2000);


                    i++;
                });
            })
            .catch(error => {
                console.log(error.response.data)
            });

    },
    methods: {
        deleteCard(n) {
            let statusCard = false;
            this.numberCard = n;
            axios.patch('/api/clients/current/cards/', `activate=${statusCard}&number=${this.numberCard}`)
                .then(response => {
                    console.log("Tarjeta eliminada");
                    window.location.href = "/web/cards.html";
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