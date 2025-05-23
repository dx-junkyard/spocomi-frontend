/* =============================
   施設・備品データ（locationData）
   ============================= */
   const remoteLocationData = [
    {
        id: "1",
        name: "スポーツセンター",
        url: "reservation_flow.html?step=2&location=西東京市A",
        position: [35.742229,139.558842],
        equipment: "バレーボール、バスケットボール",
        value: "バドミントンセット1",
        rentalItems: [
          { value: 1, label: "バレーボールコートA + ボール３個" },
          { value: 10, label: "バレーボールコートB + ボール３個" },
          { value: 11, label: "バレーボールコートAのみ借りる" },
          { value: 12, label: "バレーボールコートBのみ借りる" },
          { value: 13, label: "バレーボール(x3)のみ借りるA" },
          { value: 14, label: "バスケットボールコートA + ボール３個" },
          { value: 15, label: "バスケットボールコートBのみ借りる" },
        ]
    },
    {
        id: "2",
        name: "南町スポーツ・文化交流センター「きらっと」",
        url: "reservation_flow.html?step=2&location=南町スポーツ・文化交流センター「きらっと」",
        position: [35.725527,139.5349541],
        equipment: "バドミントンセット",
        value: "バドミントンセット",
        rentalItems: [
          { value: 2, label: "バドミントンコート1 + ラケット2本 & シャトル" },
          { value: 20, label: "バドミントンコート2 + ラケット2本 & シャトル" },
          { value: 21, label: "バドミントンコートのみ借りる" },
          { value: 22, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "3",
        name: "西東京市総合体育館",
        url: "reservation_flow.html?step=2&location=西東京市総合体育館",
        position: [35.720033,139.5273881],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセットA",
        rentalItems: [
          { value: 33, label: "バドミントンコートA + ラケット2本 & シャトル" },
          { value: 34, label: "バドミントンコートのみ借りる" },
          { value: 35, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "4",
        name: "西東京市 武道場",
        url: "reservation_flow.html?step=2&location=西東京市 武道場",
        position: [35.7465405,139.5582809],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセットB",
        rentalItems: [
          { value: 40, label: "バドミントンコートB + ラケット2本 & シャトル" },
          { value: 41, label: "バドミントンコートのみ借りる" },
          { value: 42, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "5",
        name: "芝久保運動場",
        url: "reservation_flow.html?step=2&location=芝久保運動場",
        position: [35.7233271,139.522461],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセットC",
        rentalItems: [
          { value: 50, label: "バドミントンコートC + ラケット2本 & シャトル" },
          { value: 51, label: "バドミントンコートのみ借りる" },
          { value: 52, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "6",
        name: "西東京市健康広場",
        url: "reservation_flow.html?step=2&location=西東京市健康広場",
        position: [35.7487836,139.552446],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセット",
        rentalItems: [
          { value: 60, label: "バドミントンコート + ラケット2本 & シャトル" },
          { value: 61, label: "バドミントンコートのみ借りる" },
          { value: 62, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "7",
        name: "西東京市 ひばりが丘総合運動場「ひばりアム」",
        url: "reservation_flow.html?step=2&location=西東京市 ひばりが丘総合運動場「ひばりアム」",
        position: [35.7446146,139.5350617],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセット",
        rentalItems: [
          { value: 70, label: "バドミントンコート + ラケット2本 & シャトル" },
          { value: 71, label: "バドミントンコートのみ借りる" },
          { value: 72, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "8",
        name: "西東京市 市民公園グラウンド",
        url: "reservation_flow.html?step=2&location=西東京市 市民公園グラウンド",
        position: [35.720852,139.5276726],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセット",
        rentalItems: [
          { value: 80, label: "バドミントンコート + ラケット2本 & シャトル" },
          { value: 81, label: "バドミントンコートのみ借りる" },
          { value: 82, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    },
    {
        id: "9",
        name: "西東京市 向台運動場",
        url: "reservation_flow.html?step=2&location=西東京市 向台運動場",
        position: [35.720852,139.5276726],
        equipment: "テニスラケット、バドミントンセット",
        value: "バドミントンセット",
        rentalItems: [
          { value: 90, label: "バドミントンコート + ラケット2本 & シャトル" },
          { value: 91, label: "バドミントンコートのみ借りる" },
          { value: 92, label: "バドミントンのラケット＆シャトルのみ借りる" }
        ]
    }
  ];

  
  /* =============================
     施設・備品選択のための関数
     ============================= */
  
  /**
   * 施設・備品プルダウンの生成
   */
  function populateLocationSelect() {
    const locationSelect = document.getElementById('locationSelect');
    if (!locationSelect) return; // 対象要素が存在しない場合は処理しない
    locationSelect.innerHTML = '';
    window.locationData.forEach(facility => {
      const option = document.createElement('option');
      //option.value = facility.name;  // 一意の値を利用
      option.value = facility.id;  // 一意の値を利用
      option.textContent = facility.name;
      locationSelect.appendChild(option);
    });
  }
  
  /**
   * 選択された施設に応じたレンタルアイテムの表示
   * @param {string} selectedLocationValue 施設選択で選んだ値
   */
  function populateRentalItems(selectedLocationValue) {
    const rentalItemList = document.getElementById('rentalItemList');
    if (!rentalItemList) return; // 対象要素が存在しない場合は処理しない
    rentalItemList.innerHTML = '';
    const facility = window.locationData.find(f => f.id === selectedLocationValue);
    if (facility && facility.rentalItems) {
      facility.rentalItems.forEach(item => {
        const label = document.createElement('label');
        const radio = document.createElement('input');
        radio.type = 'radio';
        radio.name = 'set';
        radio.value = item.value;
        label.appendChild(radio);
        label.appendChild(document.createTextNode(' ' + item.label));
        rentalItemList.appendChild(label);
        rentalItemList.appendChild(document.createElement('br'));
      });
    }
  }

  function populateCalendarEquipmentSelect() {
    const equipmentSelect = document.getElementById('equipment-select');
    equipmentSelect.innerHTML = '';

    console.log("populateCalendarEquipmentSelect: selectedFacility =", selectedFacility); // 追加

    // ここでは、step2で選ばれた施設（selectedFacility）に基づいて検索
    const facility = window.locationData.find(f => f.id === selectedFacility);
    console.log("populateCalendarEquipmentSelect: found facility =", facility); // 追加

    if (facility && facility.rentalItems) {
      facility.rentalItems.forEach(item => {
        const option = document.createElement('option');
        option.value = item.value;
        option.textContent = item.label;
        equipmentSelect.appendChild(option);
      });
      // Step2で選択済みの設備・備品セットがあれば、プルダウンのデフォルト選択に設定
      if(selectedEquipmentSet) {
        equipmentSelect.value = selectedEquipmentSet;
      }
      //loadEquipmentReservations(equipmentSelect.value);
    }
  }
  