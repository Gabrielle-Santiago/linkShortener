const form = document.getElementById('url-form');
const urlInput = document.getElementById('url-input');

const shortenButton = document.getElementById('shorten-btn');
const resultActions = document.getElementById('result-actions');

const copyButton = document.getElementById('copy-btn');
const resetButton = document.getElementById('reset-btn');

const message = document.getElementById('message');

let shortenedUrl = '';

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const fullUrl = urlInput.value.trim();

    if (!fullUrl) {
        message.textContent = 'Informe uma URL.';
        return;
    }

    shortenButton.disabled = true;
    message.textContent = 'Encurtando link...';

    try {
        const response = await fetch('/url', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                fullUrl: fullUrl
            })
        });

        if (!response.ok) {
            throw new Error('Não foi possível encurtar a URL.');
        }

        const shortCode = (await response.text()).trim();

        if (!shortCode) {
            throw new Error('O servidor não retornou um link válido.');
        }

        shortenedUrl = `${window.location.origin}/url/${shortCode}`;

        urlInput.value = shortenedUrl;
        urlInput.readOnly = true;

        shortenButton.hidden = true;
        resultActions.hidden = false;

        message.textContent = 'Link encurtado com sucesso!';

    } catch (error) {
        message.textContent = error.message;
    } finally {
        shortenButton.disabled = false;
    }
});

copyButton.addEventListener('click', async () => {
    try {
        await navigator.clipboard.writeText(shortenedUrl);
        message.textContent = 'Link copiado com sucesso!';
    } catch (error) {
        message.textContent = 'Não foi possível copiar o link.';
    }
});

resetButton.addEventListener('click', () => {
    urlInput.value = '';
    urlInput.readOnly = false;

    shortenedUrl = '';

    resultActions.hidden = true;
    shortenButton.hidden = false;

    message.textContent = '';

    urlInput.focus();
});
