/**********************************************************
 * ★ 変更点1: グローバル変数で「ステップ2」で選んだ設備を記憶する
 **********************************************************/
let selectedCommunity = "";       // ユーザーが選んだコミュニティの値を保持
let selectedEquipmentSet = "";      // ユーザーが選んだ設備・備品セット名を保持
let selectedFacility = "";          // ユーザーが選んだ施設の値を保持
let recruitFriends = "";

/**********************************************************
 * 予約フロー：ステップ管理
 **********************************************************/
const steps = document.querySelectorAll('.step');
const content = document.getElementById('content');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');

let currentStep = 1;
let selectedDateInfo = {
  date: null,
  startTime: null,
  endTime: null
};

window.locationData = [];

// ...（コミュニティ、施設、レンタルアイテムなどの他の関数）...

/* =============================
 コミュニティデータ（将来APIで取得する想定）
 ============================= */
const remoteCommunityData = [
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

window.communityData = [];

/* =============================
   コミュニティ用関数
   ============================= */
// コミュニティプルダウンの生成
function populateCommunitySelect() {
  const communitySelect = document.getElementById('communitySelect');
  communitySelect.innerHTML = '';
  window.communityData.forEach(community => {
    const option = document.createElement('option');
    option.value = community.id;
    option.textContent = community.name;
    communitySelect.appendChild(option);
  });
}
// 選択されたコミュニティのメンバーリストを生成
function populateCommunityMembers(selectedCommunityId) {
  const membersListContainer = document.getElementById('communityMembers');
  membersListContainer.innerHTML = '';
  const community = window.communityData.find(c => c.id === selectedCommunityId);
  if (community) {
    const ul = document.createElement('ul');
    community.members.forEach(member => {
      const li = document.createElement('li');
      li.textContent = member;
      ul.appendChild(li);
    });
    membersListContainer.appendChild(ul);
  }
}

/* =============================
 updateContent（各ステップの表示内容）
 ============================= */
function updateStepBar() {
  steps.forEach((step, index) => {
    step.classList.remove('active', 'completed');
    if (index < currentStep - 1) {
      step.classList.add('completed');
    }
    if (index === currentStep - 1) {
      step.classList.add('active');
    }
  });
}

// APIからlocationDataを更新する想定の関数（iframe関連のコードは不要なのでコメントアウト）
function fetchLocationData() {
  try {
    window.locationData = remoteLocationData;
    // 仮のデータをセット
    // locationData = [ ... ];
    
    // 以前はiframe内にデータを渡していたが、現在は不要
    // const mapIframe = document.getElementById('mapDialog').contentWindow;
    // if (mapIframe && typeof mapIframe.updateMapData === 'function') {
    //   mapIframe.updateMapData(locationData);
    // }
  } catch (error) {
    console.error('Failed to fetch location data:', error);
  }
}

function updateContent() {
  prevBtn.classList.toggle('disabled', currentStep === 1);
  nextBtn.textContent = (currentStep === 3) ? '予約する' : '次へ';
  nextBtn.classList.remove('disabled');

  switch (currentStep) {
    case 1:
      // 設備・備品セット選択
      content.innerHTML = `
        <h2>設備・備品セットを選択</h2>
        <div class="section-box">
          <h4>場所を選択</h4>
          <select id="locationSelect"></select>
          <p>地図から探す: 
             <button id="openMapDialog" class="btn" type="button">地図を表示</button>
          </p>
        </div>
        <div class="section-box">
          <h4>設備・備品を選択</h4>
          <div id="rentalItemWrapper">
            <div id="rentalItemList"></div>
          </div>
        </div>
      `;
      // 初期表示時は次へボタンを非アクティブに
      nextBtn.classList.add('disabled');
      populateLocationSelect();
      // 施設選択の値をグローバル変数に保存
      selectedFacility = document.getElementById("locationSelect").value;
      // 場所を選択のプルダウンでの選択結果を反映
      setLocationPulldown(selectedFacility);
      console.log("Case 2: selectedFacility from locationSelect:", selectedFacility); // 追加
      break;

    case 2:
      content.innerHTML = `
        <h2>日時を選択</h2>
        <p>週間カレンダーから希望の時間を選んでください。</p>
        <div id="selectedDateTime">
          <em>未選択</em>
        </div>
        <button id="openCalendarBtn" class="btn" type="button">カレンダーで日時選択</button>
      `;
      setTimeout(() => {
        populateCalendarEquipmentSelect();
        document.getElementById('openCalendarBtn').onclick = () => {
          document.getElementById('calendarDialogOverlay').style.display = 'flex';
        };
        if (selectedDateInfo.date && selectedDateInfo.startTime && selectedDateInfo.endTime) {
          document.getElementById('selectedDateTime').textContent =
            `${selectedDateInfo.date} ${selectedDateInfo.startTime}～${selectedDateInfo.endTime}`;
        } else {
          nextBtn.classList.add('disabled');
        }
      });
      break;

    case 3:
      // 施設データから、選択された設備・備品セットのオブジェクトを検索
      const facility = window.locationData.find(f => f.id === selectedFacility);
      let rentalItemLabel = '未選択';
      if (facility && facility.rentalItems) {
        const item = facility.rentalItems.find(item => item.value === selectedEquipmentSet);
        if (item) {
          rentalItemLabel = item.label;
        }
      }
      // 予約内容確認
      content.innerHTML = `
        <h2>予約内容の確認</h2>
        <p>以下の内容で予約を行います。</p>
        <div style="text-align:left; display:inline-block;">
          <p><strong>コミュニティ:</strong> ${selectedCommunity ? selectedCommunity : '未選択'}</p>
          <p><strong>施設:</strong> ${facility.name ? facility.name : '未選択'}</p>
          <p><strong>設備・備品セット:</strong> ${rentalItemLabel ? rentalItemLabel: '未選択'}</p>
          <p><strong>日時:</strong> ${
              (selectedDateInfo.date && selectedDateInfo.startTime && selectedDateInfo.endTime)
              ? `${selectedDateInfo.date} ${selectedDateInfo.startTime}～${selectedDateInfo.endTime}`
              : '未選択'
            }
          </p>
          <p>
            <input type="checkbox" id="recruitFriends" />
            <label for="recruitFriends">仲間募集</label>
            <span id="recruitInfoToggle" style="cursor:pointer; color:#8259f1; margin-left:5px;">[?]</span>
          </p>
          <div id="recruitInfo" style="display:none; font-size:0.9em; color:#555; margin-top:5px;">
            ※ 同じ備品をこの施設で借りたコミュニティのリストに加わります。
            ※ 参加コミュニティのリスト開示範囲はリスト内のコミュニティ限定です。
          </div>
        </div>
      `;
      const toggleElem = document.getElementById('recruitInfoToggle');
      if (toggleElem) {
        toggleElem.addEventListener('click', function() {
          const modalOverlay = document.createElement('div');
          modalOverlay.id = "recruitModalOverlay";
          modalOverlay.style.position = "fixed";
          modalOverlay.style.top = "0";
          modalOverlay.style.left = "0";
          modalOverlay.style.width = "100%";
          modalOverlay.style.height = "100%";
          modalOverlay.style.backgroundColor = "rgba(0,0,0,0.5)";
          modalOverlay.style.display = "flex";
          modalOverlay.style.justifyContent = "center";
          modalOverlay.style.alignItems = "center";
          modalOverlay.style.zIndex = "10000";
          
          const modalContent = document.createElement('div');
          modalContent.style.backgroundColor = "#fff";
          modalContent.style.padding = "20px";
          modalContent.style.borderRadius = "5px";
          modalContent.style.maxWidth = "80%";
          modalContent.style.textAlign = "center";
          modalContent.innerHTML = `
            <h2>仲間募集の説明</h2>
            <p>同じ備品をこの施設で借りたコミュニティのリストに加わります。参加コミュニティのリスト開示範囲はリスト内のコミュニティ限定です。</p>
            <button id="closeRecruitModal" class="btn">閉じる</button>
          `;
          modalOverlay.appendChild(modalContent);
          document.body.appendChild(modalOverlay);
          
          document.getElementById('closeRecruitModal').addEventListener('click', function() {
            document.body.removeChild(modalOverlay);
          });
        });
      }
      break;
  }
}

if (location.search) {
  const parsedQuery = Object.fromEntries(
    location.search
      .substr(1)
      .split("&")
      .map(s => s.split("="))
      .map(([k, v]) => [k, decodeURIComponent(v)])
  );
  console.log("Parsed URL query:", parsedQuery); // 追加
  if (parsedQuery.step) {
    currentStep = parseInt(parsedQuery.step, 10);
  }
  if (parsedQuery.location) {
    // facility.name として検索し、該当する施設の value を取得する
    const facilityObj = window.locationData.find(f => f.name === parsedQuery.location);
    console.log("Facility object from URL:", facilityObj); // 追加
    if (facilityObj) {
      selectedFacility = facilityObj.value;
      console.log("selectedFacility set from URL:", selectedFacility); // 追加
      // 施設選択用プルダウンが存在する場合、値を設定
      const locationSelect = document.getElementById('locationSelect');
      if (locationSelect) {
        locationSelect.value = facilityObj.value;
      }
    }
  }
}

// 次のフロー or 予約の実行
nextBtn.addEventListener('click', async (e) => {
  e.preventDefault();
  if (currentStep < 3) {
    currentStep++;
    updateStepBar();
    updateContent();
  } else {
    // 最終ステップ：ユーザーの予約内容をバックエンドへ送信
    const reservationData = {
      communityId: selectedCommunity,
      facilityId: selectedFacility,
      equipmentId: selectedEquipmentSet,
      date: selectedDateInfo.date,
      startTime: selectedDateInfo.startTime,
      endTime: selectedDateInfo.endTime,
      recruitFriends: document.getElementById('recruitFriends').checked
    };

    try {
      const response = await fetch('/v1/api/community/reservation/new', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
      });
      if (!response.ok) {
        throw new Error('Reservation submission failed');
      }
      showReservationCompleteDialog();
      setTimeout(() => { window.location.href = '/v1/mypage'; }, 1000);
    } catch (error) {
      console.error('Failed to submit reservation:', error);
      alert("予約送信に失敗しました。再度お試しください。");
    }
  }
});

function showReservationCompleteDialog() {
  const overlay = document.getElementById('reservationCompleteOverlay');
  overlay.style.display = 'flex';  // オーバーレイを表示

  const closeBtn = document.getElementById('closeReservationDialog');
  closeBtn.addEventListener('click', () => {
    overlay.style.display = 'none';  // 閉じるときは display を none に戻す
    // ホーム画面への遷移
  });
}

prevBtn.addEventListener('click', (e) => {
  e.preventDefault();
  if (currentStep > 1) {
    currentStep--;
    updateStepBar();
    updateContent();
  }
});

document.addEventListener('DOMContentLoaded', async () => {
  await fetchLocationData();
  updateStepBar();
  updateContent();  
});

updateStepBar();
updateContent();

// ----- 地図ダイアログの処理 -----
document.getElementById('closeMapDialog').addEventListener('click', function() {
  document.getElementById('mapDialogOverlay').style.display = 'none';
});


window.selectedLocation = null;

function setSelectedFacility(facility) {
  // URLエンコードされた文字列をデコード＆パースしてオブジェクトにする
  const facilityObj = JSON.parse(decodeURIComponent(facility));
  console.log("Selected facility (decoded):", facilityObj);

  locationSelect.value = facilityObj.id;

  // 施設情報をグローバル変数に保持（必要なら）
  window.selectedLocation = facilityObj.id;

  setLocationPulldown(facilityObj.id);

  // マップのダイアログを閉じる
  document.getElementById('mapDialogOverlay').style.display = 'none';
}


function initFacilityMap() {
  // マップの初期化（facility_map.html 内のコードを参考に）
  const initialCenter = [35.742229,139.558842]; // 初期中心座標
  const initialZoom = 13;
  // id="map" の div に対して地図を生成
  window.facilityMap = L.map('map').setView(initialCenter, initialZoom);

  // OpenStreetMap タイルレイヤーを追加
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(window.facilityMap);

  let facilityMarkers = [];
  let searchCircle = null;

  // マーカー作成関数
  function createFacilityMarkers() {
      facilityMarkers = window.locationData.map(facility => {
          const marker = L.marker(facility.position);
          const infoContent = `
              <div class="info-box">
                  <strong>${facility.name}</strong><br>
                  貸出用具: ${facility.equipment}<br>
                  <a href="#" onclick="setSelectedFacility('${encodeURIComponent(JSON.stringify(facility))}'); return false;">この施設で予約</a>
              </div>
          `;
          marker.bindPopup(infoContent);
          return { marker, position: facility.position };
      });
      // 初期表示時に全マーカーを追加
      facilityMarkers.forEach(({ marker }) => marker.addTo(window.facilityMap));
  }

  // 探索範囲に応じたマーカー・サークルの更新（facility_map.html の refreshMap() 相当）
  function refreshMap() {
      const radius = parseInt(document.getElementById('radiusSelect').value, 10);
      const center = window.facilityMap.getCenter();
      // マーカーの更新
      facilityMarkers.forEach(({ marker, position }) => {
          window.facilityMap.removeLayer(marker);
          const distance = window.facilityMap.distance(center, position);
          if (distance <= radius) {
              marker.addTo(window.facilityMap);
          }
      });
      // サークルの更新
      if (searchCircle) {
          window.facilityMap.removeLayer(searchCircle);
      }
      searchCircle = L.circle(center, {
          radius: radius,
          color: 'blue',
          fillColor: 'blue',
          fillOpacity: 0.1
      }).addTo(window.facilityMap);
  }

  // イベント設定：マップ移動後に探索範囲更新
  window.facilityMap.on('moveend', refreshMap);
  // 探索範囲の select の変更イベント
  document.getElementById('radiusSelect').addEventListener('change', refreshMap);
  // 現在地取得ボタンの処理
  document.getElementById('geoLocationBtn').addEventListener('click', function() {
      if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
              (position) => {
                  const lat = position.coords.latitude;
                  const lng = position.coords.longitude;
                  window.facilityMap.setView([lat, lng], 15);
              },
              (error) => {
                  alert('現在地を取得できませんでした。権限をご確認ください。');
              }
          );
      } else {
          alert('ブラウザが位置情報に対応していません。');
      }
  });

  // マーカー作成実行
  createFacilityMarkers();
  // 初回の探索範囲更新
  refreshMap();
}

function setLocationPulldown(selectedLocation) {
  nextBtn.classList.add('disabled');
  selectedFacility = selectedLocation;
  console.log("setLocationPulldownselectedLocation from locationSelect:", selectedLocation); // 追加
  loadEquipmentReservations(selectedLocation);
  populateRentalItems(selectedLocation);
  // 施設変更で備品リスト更新
  document.getElementById('locationSelect').addEventListener('change', function(e) {
    selectedLocation = e.target.value;
    console.log("Changed selectedLocation:", selectedLocation); // 追加
    populateRentalItems(e.target.value);
    nextBtn.classList.add('disabled');
    // 新たに生成されたラジオボタンにイベントリスナーを登録
    const setRadios = document.querySelectorAll('input[name="set"]');
    setRadios.forEach(radio => {
      radio.addEventListener('change', (e) => {
        selectedEquipmentSet = e.target.value;
        nextBtn.classList.remove('disabled');
      });
    });
  });

  document.getElementById('openMapDialog').addEventListener('click', function() {
      document.getElementById('mapDialogOverlay').style.display = "flex";
      // 既に初期化されていなければ、マップを初期化
      if (!window.facilityMap) {
          initFacilityMap();
      }
  });

  setTimeout(() => {
    const setRadios = document.querySelectorAll('input[name="set"]');
    setRadios.forEach(radio => {
      radio.addEventListener('change', (e) => {
        selectedEquipmentSet = e.target.value;
        nextBtn.classList.remove('disabled');
      });
    });
  });
}