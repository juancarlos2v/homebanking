const idParam = window.location.search;
console.log(idParam);
const urlParams = new URLSearchParams(idParam);
var number = urlParams.get('number');
console.log(number);

let app = new Vue({
    el: '#app2',
    data: {
        client: "",
        transactions: [],
        cuenta: [],
        t: [],
        desde: "",
        hasta: "",
    },
    mounted() {
        axios.get(`/api/accounts/${number}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(respuesta => {

                this.cuenta = respuesta.data;
                this.transactions = respuesta.data.transactions;
                this.transactions.sort((a, b) => b.id - a.id);
                console.log(this.transactions);
                //console.log(this.cuenta);
                let rest = 0;
                let fecha;
                for (let index = 0; index < this.transactions.length; index++) {
                    if (index == 0) {
                        if (this.transactions[index].type == 'DEBITO') {
                            rest = this.cuenta.balance + this.transactions[index].amount;
                        }
                        if (this.transactions[index].type == 'CREDITO') {
                            rest = this.cuenta.balance - this.transactions[index].amount;
                        }
                        this.transactions[index].resto = rest;

                    }
                    //this.transactions[index].fecha = "";
                    fecha = new Date(this.transactions[index].date);
                    //console.log(fecha);
                    this.transactions[index].fecha = `${fecha.getDate()}/${fecha.getMonth()+1}/${fecha.getFullYear()}`
                    this.transactions[index].fechaHora = `${fecha.getDate()}/${fecha.getMonth()+1}/${fecha.getFullYear()} ${fecha.getHours()}:${fecha.getMinutes()}`
                        //console.log(this.transactions[index].fecha);
                };
                for (let j = 1; j < this.transactions.length; j++) {
                    if (this.transactions[j].type == 'DEBITO') {
                        rest += this.transactions[j].amount

                    }
                    if (this.transactions[j].type == 'CREDITO') {
                        rest -= this.transactions[j].amount
                    }
                    this.transactions[j].resto = rest;
                }
            });
        this.client = sessionStorage.getItem('nombre');
        console.log(this.client);
    },
    methods: {
        dateFilter() {
            let filtro = [];
            this.transactions.forEach(transaction => {
                let desd = new Date(this.desde);
                let hast = new Date(this.hasta);
                let trans = new Date(transaction.fecha);
                if (trans.getFullYear() >= desd.getFullYear() &&
                    trans.getMonth() >= desd.getMonth() &&
                    trans.getDate() >= desd.getDate() &&
                    trans.getFullYear() <= hast.getFullYear() &&
                    trans.getMonth() <= hast.getMonth() &&
                    trans.getDate() <= hast.getDate() ||

                    trans.getFullYear() <= desd.getFullYear() &&
                    trans.getMonth() <= desd.getMonth() &&
                    trans.getDate() <= desd.getDate() &&
                    trans.getFullYear() >= hast.getFullYear() &&
                    trans.getMonth() >= hast.getMonth() &&
                    trans.getDate() >= hast.getDate()) {
                    filtro.push(transaction);
                    console.log(transaction.date);
                }
            })
            console.log(filtro);
            this.transactions = [{}];
            this.transactions = filtro;
        },
        showAll() {
            this.transactions = [{}];
            this.desde = "";
            this.hasta = "";
            this.transactions = this.cuenta.transactions;
        }
    },

});
window.addEventListener("load", function() {
    document.getElementById("onload").classList.toggle("loader2");
})


//window.jsPDF = window.jspdf.jsPDF;




function saveDoc() {
    window.jsPDF = window.jspdf.jsPDF;
    //window.html2canvas = html2canvas;
    var pdf = new jsPDF('p', 'pt', [1200, 1450]); // 'pt', 'a4'
    pdf.setFontSize(10);
    const contenedor = document.getElementsByClassName('contenedor-movimientos')[0];


    var specialElementHandler = {
        '#editor': function(element, renderer) {
            return true;
        }
    }

    pdf.html(contenedor, {
        'margin': 30,
        'x': 3,
        'y': 3,
        "height": 40,
        "width": 100,
        "elementHandlers": specialElementHandler,
        callback: function(pdf) {

            pdf.save('DOC.pdf');
        }
    })
}


function impr() {
    const contenedor = document.getElementsByClassName('contenedor-movimientos')[0];
    html2pdf()
        .set({
            margin: 1,
            with: 1,
            height: 1,
            filename: 'transaction.pdf',
            html2canvas: {
                scale: 1,
                letterRendering: true,
            },
            jsPDF: {
                unit: "mm",
                format: "a4",
                orientation: 'portrait'
            }
        })
        .from(contenedor)
        .save()
        .then(() => {
            console.log("print!");
        })
}