const sidebar = document.getElementById("sidebar");
const main = document.getElementById("main");
const btnOpen = document.getElementById("btn-open-sidebar");
const btnClose = document.getElementById("btn-close-sidebar");

btnOpen.onclick = () => {
    sidebar.style.width = "250px";    // abre a sidebar
    main.style.marginLeft = "250px";  // empurra conteúdo
};

btnClose.onclick = () => {
    sidebar.style.width = "0";        // fecha a sidebar
    main.style.marginLeft = "0";      // volta conteúdo
};
