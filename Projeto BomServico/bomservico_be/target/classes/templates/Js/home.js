/* Home scripts — Bom Serviço
   Usa jQuery + Fetch. Fallback se a API ainda não existir.
   Endpoints esperados:
   - GET /api/categorias                -> [{id, nome, slug}]
   - GET /api/anuncios?limit=8&status=publicado&categoria=slug&busca=txt
*/

(function ($) {
    "use strict";

    const $grid = $("#grid-anuncios");
    const $cats = $("#lista-categorias");
    const $campoBusca = $("#campo-busca");
    const $formBusca = $("#form-busca");
    const $scrollTop = $("#scrolltop");

    // Utils
    const slugify = (s) =>
        String(s || "")
            .toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .replace(/[^a-z0-9]+/g, "-")
            .replace(/(^-|-$)+/g, "");

    const fmtMoney = (v) =>
        typeof v === "number"
            ? v.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
            : v;

    const year = new Date().getFullYear();
    $("#ano-atual").text(year);

    // Sugestões rápidas
    $("[data-bsrv-sugestao]").on("click", function () {
        $campoBusca.val($(this).data("bsrvSugestao"));
        $formBusca.trigger("submit");
    });

    // Busca → redireciona para a listagem com querystring
    $formBusca.on("submit", function (e) {
        e.preventDefault();
        const q = ($campoBusca.val() || "").trim();
        if (!q) return $campoBusca.trigger("focus");
        try { localStorage.setItem("bsrv:lastQuery", q); } catch {}
        window.location.href = "/anuncios?busca=" + encodeURIComponent(q);
    });

    // ScrollTop
    $(window).on("scroll", function () {
        if (window.scrollY > 300) $scrollTop.fadeIn(120);
        else $scrollTop.fadeOut(120);
    });
    $scrollTop.on("click", () => window.scrollTo({ top: 0, behavior: "smooth" }));

    // Renderizadores
    function renderCategorias(lista) {
        $cats.empty();
        lista.forEach((c) => {
            const slug = c.slug || slugify(c.nome);
            const pill = $(`
        <button class="btn bsrv-cat-pill btn-sm rounded-pill px-3 py-2"
                data-categoria="${slug}" title="Ver ${c.nome}">
          <i class="bi bi-tag me-1"></i>${c.nome}
        </button>
      `);
            pill.on("click", () => (window.location.href = "/anuncios?categoria=" + encodeURIComponent(slug)));
            $cats.append(pill);
        });
    }

    function renderAnuncios(lista) {
        $grid.empty();
        if (!lista?.length) {
            $grid.append(`
        <div class="col-12">
          <div class="alert alert-light border">
            <i class="bi bi-info-circle me-2"></i>Nenhum anúncio encontrado.
          </div>
        </div>
      `);
            return;
        }

        lista.forEach((a) => {
            const preco = a.preco ? fmtMoney(a.preco) : "A combinar";
            const img = (a.fotos?.[0]) || "https://picsum.photos/seed/servico"+(a.id || Math.random())+"/640/360";
            const card = $(`
        <div class="col-12 col-sm-6 col-lg-3">
          <div class="card bsrv-card rounded-4 border-0 shadow-sm">
            <img class="card-img-top" src="${img}" alt="${a.titulo || "Serviço"}" loading="lazy">
            <div class="card-body">
              <h6 class="card-title mb-1">${a.titulo || "Serviço"}</h6>
              <div class="small text-secondary mb-2">${a.categoria || "Categoria"}</div>
              <p class="card-text small text-body-secondary">${(a.resumo || a.descricao || "").toString().slice(0, 110)}...</p>
              <div class="d-flex justify-content-between align-items-center">
                <span class="fw-semibold">${preco}</span>
                <a href="/anuncios/${a.id || "#"}" class="btn btn-sm btn-primary">
                  <i class="bi bi-chevron-right"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      `);
            $grid.append(card);
        });
    }

    // Fetch com fallback
    async function fetchJSON(url) {
        const r = await fetch(url, { headers: { "Accept": "application/json" } });
        if (!r.ok) throw new Error("HTTP " + r.status);
        return r.json();
    }

    async function carregarCategorias() {
        try {
            const data = await fetchJSON("/api/categorias");
            renderCategorias(data);
        } catch {
            // Fallback local
            renderCategorias([
                { id: 1, nome: "Reparos" },
                { id: 2, nome: "Transporte" },
                { id: 3, nome: "Culinária" },
                { id: 4, nome: "Limpeza" },
                { id: 5, nome: "Jardinagem" },
                { id: 6, nome: "Tecnologia" }
            ]);
        }
    }

    async function carregarDestaques() {
        try {
            const data = await fetchJSON("/api/anuncios?limit=8&status=publicado");
            renderAnuncios(data);
        } catch {
            // Fallback local
            renderAnuncios([
                { id: 101, titulo: "Eletricista Residencial", categoria: "Reparos", resumo: "Instalações, reparos e emergências.", preco: 120 },
                { id: 102, titulo: "Diarista de Confiança", categoria: "Limpeza", resumo: "Limpeza semanal, quinzenal e pós-obra." },
                { id: 103, titulo: "Frete & Carreto", categoria: "Transporte", resumo: "Carretos na cidade e região.", preco: 80 },
                { id: 104, titulo: "Bolos e Doces Artesanais", categoria: "Culinária", resumo: "Encomendas para festas e eventos." },
                { id: 105, titulo: "Manutenção de Jardins", categoria: "Jardinagem", resumo: "Poda, adubação e limpeza." },
                { id: 106, titulo: "Suporte de Informática", categoria: "Tecnologia", resumo: "Formatação, upgrade e redes.", preco: 150 },
                { id: 107, titulo: "Pintor Profissional", categoria: "Reparos", resumo: "Pintura interna e externa." },
                { id: 108, titulo: "Montagem de Móveis", categoria: "Reparos", resumo: "Rápido e sem dor de cabeça.", preco: 90 }
            ]);
        }
    }

    // Boot
    (async function init() {
        await Promise.all([carregarCategorias(), carregarDestaques()]);
    })();

})(jQuery);
