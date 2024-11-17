document.addEventListener("DOMContentLoaded", () => {
    const dropdown = document.querySelector(".custom-dropdown");
    const dropdownContentBox = document.querySelector(".custom-dropdown-content-box");
    const dropdownItems = document.querySelectorAll(".custom-dropdown-content");
    const hiddenInput = document.getElementById("searchType");
    const dropdownSpan = dropdown.querySelector("span");

    // 드롭다운 활성화/비활성화
    dropdown.addEventListener("click", (e) => {
        e.stopPropagation();
        dropdown.classList.toggle("active");

        dropdownContentBox.classList.toggle("none");
    });

    // 드롭다운 항목 클릭 시 동작
    dropdownItems.forEach(item => {
        item.addEventListener("click", () => {
            // 기존 선택 항목 초기화
            dropdownItems.forEach(i => i.classList.remove("selected"));

            // 선택된 항목에 'selected' 클래스 추가
            item.classList.add("selected");

            // 선택된 data-value를 hidden input 및 드롭다운 텍스트에 반영
            hiddenInput.value = item.dataset.value;
            dropdownSpan.textContent = item.textContent;


        });
        // 드롭다운 닫기
        dropdown.classList.remove("active");
        dropdownContentBox.classList.add("none");
    });

    // 드롭다운 외부 클릭 시 닫기
    document.addEventListener("click", () => {
        dropdown.classList.remove("active");
        dropdownContentBox.classList.add("none");
    });
});