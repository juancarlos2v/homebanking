let app = new Vue({
    el: '#app',
    data: {
        clientes: [],
        json: [],
        form: {
            "firstName": "",
            "lastName": "",
            "email": "",
        }
    },
    mounted() {
        axios.get('rest/clients')
            .then(respuesta => {
                //obtenemos datos
                this.clientes = respuesta.data._embedded.clients
                this.json = respuesta.data;
                console.log(this.clientes);
            })
            .catch(error => {
                console.log(error)
            });
    },
    methods: {
        loadData: function() {
            if (this.form.firstName && this.form.lastName && this.form.email.includes('@')) {
                console.log(this.form);
                axios.post('rest/clients', this.form).then(result => { console.log(result) });
                alert("Cliente agregado.");
            } else {
                alert("Todos los campos  son obligatorios!")
            }
        }

    }
});