let app = new Vue({
    el: '#app',
    data: {
        form: {
            nombre: "",
            monto: 0,
            cuotasInput: "",
            cuotas: [],
            rate: 0,
        }
    },
    mounted() {

    },
    methods: {
        creationLoan() {
            this.form.cuotas = this.form.cuotasInput.split(',').map(function(item) {
                return parseInt(item, 10);
            });
            axios.put('/api/loans', { name: this.form.nombre, maxAmount: this.form.monto, payments: this.form.cuotas, rate: this.form.rate })
                .then(response => {
                    console.log("Prestamo creado");
                })
                .catch(error => {
                    console.log(error.response.data)
                })
        },
        signout() {
            axios.post('/api/logout').then(response => {
                    console.log("salir");
                    window.location.href = "/web/index-admin.html";
                })
                .catch(error => {
                    console.log(error)
                })
        }
    },
})