// visibilityOptions.js
const visibilityOptions = {
    1: "公開",
    2: "地域限定",
    3: "非公開"
};

// 選択肢を作成する関数
function createVisibilityOptions(selectElementId) {
    const selectElement = document.getElementById(selectElementId);

    // visibilityOptionsをループして<option>要素を作成
    for (const [value, label] of Object.entries(visibilityOptions)) {
        const option = document.createElement("option");
        option.value = value;
        option.textContent = label;
        selectElement.appendChild(option);
    }
}

// Visibility情報を表示する関数
function displayVisibility(visibilityValue, displayElementId) {
    const displayElement = document.getElementById(displayElementId);
    displayElement.textContent = visibilityOptions[visibilityValue] || "不明";
}
