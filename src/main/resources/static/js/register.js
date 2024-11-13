document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.createElement('form');
    registerForm.method = 'POST';
    registerForm.action = '/register'; // 경로 수정

    const article = document.querySelector('article');
    const joinButton = document.querySelector('#join-btn');

    console.log(joinButton); // joinButton 선택 확인용

    joinButton.addEventListener('click', function(e) {
        e.preventDefault();

        const email = document.querySelector('#email');
        const password = document.querySelector('#pw');
        const confirmPassword = document.querySelector('#pwCheck');

        // 이미 추가된 form이 다시 append되는 걸 방지
        if (!article.contains(registerForm)) {
            article.appendChild(registerForm);
        }

        registerForm.innerHTML = ''; // form 재생성 방지

        const emailInput = document.createElement('input');
        emailInput.type = 'hidden';
        emailInput.name = 'email';
        emailInput.value = email.value;

        const passwordInput = document.createElement('input');
        passwordInput.type = 'hidden';
        passwordInput.name = 'password';
        passwordInput.value = password.value;

        const confirmPasswordInput = document.createElement('input');
        confirmPasswordInput.type = 'hidden';
        confirmPasswordInput.name = 'confirmPassword';
        confirmPasswordInput.value = confirmPassword.value;

        registerForm.appendChild(emailInput);
        registerForm.appendChild(passwordInput);
        registerForm.appendChild(confirmPasswordInput);

        registerForm.submit();
    });
});
