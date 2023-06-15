let app = new Vue({
  el: "#app",
  data: {
    form: {
      user: "",
      pass: "",
    },
    clientes: [],
    id: false,
    formEstado: false,
    error: false,
  },
  methods: {
    login() {
      axios
        .post(
          "/api/login",
          `email=${this.form.user}&password=${this.form.pass}`,
          {
            headers: {
              "content-type": "application/x-www-form-urlencoded",
            },
          }
        )
        .then((response) => {
          console.log(this.form.user);
          console.log(this.form.pass);
          window.location.href = "/web/accounts.html";
        })
        .catch((error) => {
          this.error = true;
          this.form.user = "";
          this.form.pass = "";
        });
    },
    //   const url = "http://localhost:8080/api/login";
    //   const data = `email=${this.form.user}&password=${this.form.pass}`;
    //   fetch(url, {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //       Accept: "*/*",
    //       Credentials: "include",
    //     },
    //     body: JSON.stringify(data),
    //   })
    //     .then((response) => response.json())
    //     .then((data) => {
    //       console.log("Respuesta:", data);
    //     })
    //     .catch((error) => {
    //       console.error("Error:", error);
    //     });
    // },
    showForm() {
      if (this.formEstado == false) {
        this.formEstado = true;
      } else if (this.formEstado == true) {
        this.formEstado = false;
      }
      console.log(this.formEstado);
    },
  },
});

window.addEventListener("load", function () {
  document.getElementById("onload").classList.toggle("loader2");
});
sessionStorage.clear();

// loguear() {
//     this.clientes.forEach(cliente => {
//         if (cliente.email == this.form.user && cliente.password == this.form.pass) {
//             sessionStorage.id = cliente.id;
//             this.id = true;
//             window.location.href = "/web/accounts.html";
//         };
//     });
//     if (this.id == false) {
//         this.error = true;
//         this.form.user = "";
//         this.form.pass = "";
//     }
// },
