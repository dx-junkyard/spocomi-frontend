//var networkingInstance = /*[[${networking}]]*/ {};

var myCommunityId = document.querySelector('#networking-container').getAttribute('data-my-community-id');
var myCommunityName = document.querySelector('#networking-container').getAttribute('data-my-community-name');
var partnerCommunityId = document.querySelector('#networking-container').getAttribute('data-partner-community-id');
var partnerCommunityName = document.querySelector('#networking-container').getAttribute('data-partner-community-name');
networkingInstance = {
    myCommunityId: myCommunityId,
    myCommunityName: myCommunityName,
    partnerCommunityId: partnerCommunityId ? partnerCommunityId.split(',') : [],
    partnerCommunityName: partnerCommunityName ? partnerCommunityName.split(',') : []
};


function searchCommunity(button) {
    const row = button.parentElement;
    const input = row.querySelector('.community-id-input');
    const communityNameDiv = row.querySelector('.community-name');
    const deleteButton = row.querySelector('.delete-btn');

    if (input.value.trim() === "") {
        alert("コミュニティIDを入力してください");
        return;
    }

    const communityId = input.value.trim();

    // Check if the input is a valid number
    if (isNaN(communityId)) {
        alert("そのidは選択できません");
        return;
    }

    // Check if the communityId matches myCommunityId in networkingInstance
    if (communityId === networkingInstance.myCommunityId.toString()) {
        alert("そのidは選択できません");
        return;
    }


    // Fetch community name from API using the communityId
    fetch(`/v1/api/community/${communityId}/community-name`)
        .then(response => {
            if (!response.ok) {
                throw new Error('コミュニティが見つかりません');
            }
            return response.json();
        })
        .then(data => {
            communityNameDiv.textContent = data.communityName;

            // Update the Networking instance with the new community
            updateNetworkingInstance(communityId, data.communityName);

            // Disable search button, activate delete button
            button.disabled = true;
            button.style.display = "none";
            deleteButton.style.display = "inline-block";

            // Add a new row for the next community
            addNewCommunityRow();
        })
        .catch(error => {
            alert(error.message);
        });
}

function deleteCommunity(button) {
    const row = button.parentElement;
    const input = row.querySelector('.community-id-input');
    const communityId = input.value.trim();
    const container = document.getElementById('community-container');

    container.removeChild(row);

    // Remove the community from the Networking instance
    removeCommunityFromNetworkingInstance(communityId);

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

function updateNetworkingInstance(communityId, communityName) {
    // Assuming Networking instance is available globally as networkingInstance
    if (!networkingInstance.partnerCommunityId) {
        networkingInstance.partnerCommunityId = [];
    }
    if (!networkingInstance.partnerCommunityName) {
        networkingInstance.partnerCommunityName = [];
    }
    networkingInstance.partnerCommunityId.push(communityId);
    networkingInstance.partnerCommunityName.push(communityName);
}

function removeCommunityFromNetworkingInstance(communityId) {
    // Assuming Networking instance is available globally as networkingInstance
    const index = networkingInstance.partnerCommunityId.indexOf(communityId);
    if (index > -1) {
        networkingInstance.partnerCommunityId.splice(index, 1);
        networkingInstance.partnerCommunityName.splice(index, 1);
    }
}

// Add event listener for create group button to send POST request
document.getElementById('create-group-btn').addEventListener('click', () => {
    fetch('/v1/api/community/create-group', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(networkingInstance)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('グループの作成に失敗しました');
            }
            return response.json();
        })
        .then(data => {
            alert('グループが正常に作成されました');
            // Navigate to the community introduction page
            const newCommunityId = data;
            const link = document.createElement('a');
            link.href = `/v1/community/${newCommunityId}/edit`;
            link.style.display = 'none';
            document.body.appendChild(link);
            link.click(); // Simulate a click on the link
        })
        .catch(error => {
            alert(error.message);
        });
});
