const API_URL = '/api';

document.addEventListener('DOMContentLoaded', () => {
    carregarFiltrosMarcas();
    carregarVeiculos();

    document.getElementById('btnNovoVeiculo').addEventListener('click', abrirModalNovoVeiculo);
    document.querySelector('.close').addEventListener('click', fecharModal);
    document.getElementById('formVeiculo').addEventListener('submit', salvarVeiculo);
    document.getElementById('btnFiltrar').addEventListener('click', carregarVeiculos);
    document.getElementById('selectMarca').addEventListener('change', (e) => carregarSelectModelos(e.target.value));
});

async function carregarFiltrosMarcas() {
    try {
        const res = await fetch(`${API_URL}/marcas`);
        const marcas = await res.json();
        const filter = document.getElementById('filterMarca');
        const select = document.getElementById('selectMarca');
        
        marcas.forEach(m => {
            filter.innerHTML += `<option value="${m.id}">${m.nome}</option>`;
            select.innerHTML += `<option value="${m.id}">${m.nome}</option>`;
        });
    } catch (error) {
        console.error("Erro ao carregar marcas", error);
    }
}

async function carregarSelectModelos(marcaId) {
    if (!marcaId) return;
    try {
        const res = await fetch(`${API_URL}/modelos?marcaId=${marcaId}`);
        const modelos = await res.json();
        const select = document.getElementById('selectModelo');
        select.innerHTML = '';
        modelos.forEach(m => {
            select.innerHTML += `<option value="${m.id}">${m.nome}</option>`;
        });
    } catch(error) {
        console.error("Erro ao carregar modelos", error);
    }
}

async function carregarVeiculos() {
    const marcaId = document.getElementById('filterMarca').value;
    const status = document.getElementById('filterStatus').value;
    const grid = document.getElementById('veiculosGrid');

    grid.innerHTML = '<p class="empty-state">Carregando veículos...</p>';
    
    let url = `${API_URL}/veiculos?`;
    if (marcaId) url += `marcaId=${marcaId}&`;
    if (status) url += `status=${status}&`;

    try {
        const res = await fetch(url);
        if (!res.ok) {
            throw new Error('Não foi possível carregar os veículos');
        }

        const veiculos = await res.json();
        renderizarVeiculos(veiculos);
    } catch (error) {
        console.error("Erro ao carregar veículos", error);
        grid.innerHTML = '<p class="empty-state">Não foi possível carregar os veículos.</p>';
    }
}

function renderizarVeiculos(veiculos) {
    const grid = document.getElementById('veiculosGrid');
    grid.innerHTML = '';

    if (!Array.isArray(veiculos) || veiculos.length === 0) {
        grid.innerHTML = '<p class="empty-state">Nenhum veículo encontrado.</p>';
        return;
    }

    veiculos.forEach(v => {
        const marca = v.modelo?.marca?.nome || 'Marca não informada';
        const modelo = v.modelo?.nome || 'Modelo não informado';
        const ano = v.ano || '-';
        const cor = v.cor || '-';
        const quilometragem = Number(v.quilometragem || 0).toLocaleString('pt-BR');
        const formatPreco = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v.preco || 0);
        const statusClass = v.status === 'Disponível' ? 'disponivel' : 'vendido';
        
        grid.innerHTML += `
            <div class="card">
                <div class="card-header">
                    <div>
                        <span class="card-brand">${marca}</span>
                        <h3>${modelo}</h3>
                    </div>
                    <span class="badge ${statusClass}">${v.status || 'Sem status'}</span>
                </div>

                <p class="price">${formatPreco}</p>

                <div class="car-info">
                    <span>Ano<strong>${ano}</strong></span>
                    <span>Cor<strong>${cor}</strong></span>
                    <span>Km<strong>${quilometragem}</strong></span>
                </div>
                
                <div class="card-actions">
                    <button class="btn secondary" onclick="marcarVendido(${v.id})" ${v.status === 'Vendido' ? 'disabled' : ''}>Vender</button>
                    <button class="btn danger" onclick="deletarVeiculo(${v.id})">Excluir</button>
                </div>
            </div>
        `;
    });
}

function abrirModalNovoVeiculo() {
    document.getElementById('modalVeiculo').style.display = 'flex';
    document.getElementById('formVeiculo').reset();
    const primeiraMarca = document.getElementById('selectMarca').value;
    if (primeiraMarca) carregarSelectModelos(primeiraMarca);
}

function fecharModal() {
    document.getElementById('modalVeiculo').style.display = 'none';
}

async function salvarVeiculo(e) {
    e.preventDefault();
    
    const veiculo = {
        modelo: { id: document.getElementById('selectModelo').value },
        ano: document.getElementById('inputAno').value,
        cor: document.getElementById('inputCor').value,
        preco: document.getElementById('inputPreco').value,
        quilometragem: document.getElementById('inputKm').value,
        status: document.getElementById('selectStatus').value
    };

    try {
        const res = await fetch(`${API_URL}/veiculos`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(veiculo)
        });

        if (!res.ok) {
            throw new Error('Não foi possível salvar o veículo');
        }

        fecharModal();
        document.getElementById('filterMarca').value = '';
        document.getElementById('filterStatus').value = '';
        carregarVeiculos();
    } catch (error) {
        alert('Erro ao salvar veículo');
    }
}

async function deletarVeiculo(id) {
    if (confirm('Tem certeza que deseja remover este veículo?')) {
        await fetch(`${API_URL}/veiculos/${id}`, { method: 'DELETE' });
        carregarVeiculos();
    }
}

async function marcarVendido(id) {
    await fetch(`${API_URL}/veiculos/${id}/status`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'text/plain' },
        body: 'Vendido'
    });
    carregarVeiculos();
}
