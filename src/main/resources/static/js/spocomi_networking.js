function searchCommunity(button) {
    const row = button.parentElement;
    const input = row.querySelector('.community-id-input');
    const communityNameDiv = row.querySelector('.community-name');
    const deleteButton = row.querySelector('.delete-btn');

    if (input.value.trim() === "") {
        alert("コミュニティIDを入力してください");
        return;
    }

    // Simulate search and result display
    communityNameDiv.textContent = "サッカー愛好会"; // Example of a community name

    // Disable search button, activate delete button
    button.disabled = true;
    button.style.display = "none";
    deleteButton.style.display = "inline-block";

    // Add a new row for the next community
    addNewCommunityRow();
}

function deleteCommunity(button) {
    const row = button.parentElement;
    const container = document.getElementById('community-container');

    container.removeChild(row);

    // Check if any rows remain for activating group creation button
    checkGroupCreationButton();
}
function addNewCommunityRow() {
    const container = document.getElementById('community-container');
    
    const newRow = document.createElement('div');
    newRow.className = 'community-row';
    newRow.innerHTML = `
        <input type="text" class="community-id-input" placeholder="コミュニティIDを入力">
        <div class="community-name">- コミュニティ名 -</div>
        <button class="search-btn" onclick="searchCommunity(this)">検索</button>
        <button class="delete-btn" onclick="deleteCommunity(this)">削除</button>
    `;

    container.appendChild(newRow);

    checkGroupCreationButton();
}

function checkGroupCreationButton() {
    const rows = document.querySelectorAll('.community-row');
    const createGroupBtn = document.getElementById('create-group-btn');

    // If there is more than 1 row with a delete button active, enable the group creation button
    let activeRows = 0;
    rows.forEach(row => {
        const deleteButton = row.querySelector('.delete-btn');
        if (deleteButton.style.display === 'inline-block') {
            activeRows++;
        }
    });

    if (activeRows > 0) {
        createGroupBtn.disabled = false;
        createGroupBtn.classList.add('active');
    } else {
        createGroupBtn.disabled = true;
        createGroupBtn.classList.remove('active');
    }
}
