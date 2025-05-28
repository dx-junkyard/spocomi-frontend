const content = document.getElementById('content');


/* =============================
 コミュニティデータ（将来APIで取得する想定）
 ============================= */
/*
 const communityData = [
    {
      id: "com1",
      name: "コミュニティA",
      members: ["ともき", "かとう", "フランソワ"]
    },
    {
      id: "com2",
      name: "コミュニティB",
      members: ["ユーザー1", "ユーザー2", "ユーザー3"]
    },
    {
      id: "com3",
      name: "コミュニティC",
      members: ["メンバーA", "メンバーB"]
    }
  ];
*/

/* =============================
   コミュニティ用関数
   ============================= */
// コミュニティプルダウンの生成
function populateCommunitySelect(communityData) {
    const communitySelect = document.getElementById('communitySelect');
    communitySelect.innerHTML = '';
    communityData.forEach(community => {
      const option = document.createElement('option');
      option.value = community.id;
      option.textContent = community.name;
      communitySelect.appendChild(option);
    });
  }
  // 選択されたコミュニティのメンバーリストを生成
  function populateCommunityMembers(selectedCommunityId, communityData) {
    const membersListContainer = document.getElementById('communityMembers');
    membersListContainer.innerHTML = '';
    /*
    const community = communityData.find(c => c.id === selectedCommunityId);
    if (community) {
      const ul = document.createElement('ul');
      community.members.forEach(member => {
        const li = document.createElement('li');
        li.textContent = member;
        ul.appendChild(li);
      });
      membersListContainer.appendChild(ul);
    }
    */
    // 予約リンクのhrefを更新
    const reservationLink = document.getElementById('reservationLink');
    if (reservationLink) {
        console.log("selectedCommunityId:", selectedCommunityId);
        reservationLink.href = `/v1/reservation/${selectedCommunityId}/new`;
    }
  }

async function updateContent() {
    // コミュニティ選択・登録
    content.innerHTML = `
      <h2>活動したいコミュニティを選択</h2>
      <div class="input-group">
        <select id="communitySelect" style="margin-top: 5px; width: 200px;"></select>
      </div>
    `;
    let communityData = await fetchMyCommunityData();
    populateCommunitySelect(communityData);
    const communitySelect = document.getElementById('communitySelect');
    const firstCommunityId = communitySelect.value;
    populateCommunityMembers(firstCommunityId, communityData);

    selectedCommunity = communitySelect.value;
    document.getElementById('communitySelect').addEventListener('change', function(e) {
      populateCommunityMembers(e.target.value);
      selectedCommunity = e.target.options[e.target.selectedIndex].text;
    });
}

function fetchMyCommunityData() {
    return fetch(`/v1/api/community/my-community-list`)
        .then(response => {
            if (!response.ok) {
                throw new Error('コミュニティが見つかりません');
            }
            return response.json();
        })
        .catch(error => {
            alert(error.message);
            return [];
        });
}


updateContent();
