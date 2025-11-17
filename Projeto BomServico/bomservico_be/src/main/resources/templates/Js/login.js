async function fazerLogin() {
    event.preventDefault();
    const login = document.getElementById('usu_login').value;
    const senha = document.getElementById('usu_senha').value;

    try{
        const response = await fetch('http://localhost:8080/api/autenticacao/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                login: login,
                senha: senha
            })
        });

        if (response.ok){
            const token = await response.text();
            localStorage.setItem('jwtToken', token);
            try {
                const checkDashboard = await fetch('dashboard.html', { method: 'HEAD' });
                if (checkDashboard.ok) {
                    window.location.href = 'dashboard.html';
                } else {
                    alert('Login realizado! Dashboard em desenvolvimento.');
                    console.log('Token:', token);
                }
            } catch (e) {
                alert('Login realizado! Token salvo com sucesso.');
                console.log('Token:', token);
            }
        }
        else{
            const erro = await response.json();
            alert(erro.mensagem || 'Erro ao fazer login');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao conectar com servidor');
    }
}
