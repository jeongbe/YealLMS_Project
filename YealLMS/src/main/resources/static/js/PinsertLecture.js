document.addEventListener('DOMContentLoaded', function () {
    let sumBtn = document.querySelector('.sum_btn');

    let LecName = document.getElementById('LecName');
    let ProName = document.getElementById('ProName');
    let LecCredit = document.getElementById('LecCredit');
    let ProPhone = document.getElementById('ProPhone');
    let LetStart = document.getElementById('LetStart');
    let LetClose = document.getElementById('LetClose');
    let ApplyStart = document.getElementById('ApplyStart');
    let ApplyClose = document.getElementById('ApplyClose');
    let SCnt = document.getElementById('SCnt');
    let LecPurpose = document.getElementById('LecPurpose');
    let AppCheck = document.getElementById('AppCheck');
    let AppTask = document.getElementById('AppTask');
    let AppMiddle = document.getElementById('AppMiddle');
    let AppLast = document.getElementById('AppLast');

    let inputs = [LecName, ProName, LecCredit, ProPhone, LetStart, LetClose, ApplyStart, ApplyClose, SCnt, LecPurpose, AppCheck, AppTask, AppMiddle, AppLast];

    inputs.forEach(input => {
        input.addEventListener('input', checkInputs);
    });

    function checkInputs() {
        let allFilled = inputs.every(input => input.value.trim() !== '');

        if (allFilled) {
            sumBtn.removeAttribute('disabled');
        } else {
            sumBtn.setAttribute('disabled', 'disabled');
        }
    }

    sumBtn.onclick = () => {
        console.log("활성화");
        alert("활성화");
    };
});
