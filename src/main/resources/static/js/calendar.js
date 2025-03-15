
const facilityClosureSchedules = {
  //"バレーボールセットA": [
  1: [
    { day: 0, date: "2025-03-10", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 0, date: "2025-03-10", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" }
  ],
  //"バドミントンセット1": [
  2: [
    { day: 0, date: "2025-03-10", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 0, date: "2025-03-10", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" }
  ],
  // "バドミントンセット2": [
  3: [
    { day: 0, date: "2025-03-10", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 0, date: "2025-03-10", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 1, date: "2025-03-11", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 2, date: "2025-03-12", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 3, date: "2025-03-13", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 4, date: "2025-03-14", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 5, date: "2025-03-15", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "08:00", endTime: "10:00", eventText: "閉鎖" },
    { day: 6, date: "2025-03-16", startTime: "21:00", endTime: "24:00", eventText: "閉鎖" }
  ]
};

const equipmentReservations = {
  //"バレーボールセットA": [
    1: [
      { day: 0, date: "2025-03-10", startTime: "10:00", endTime: "11:00", eventText: "予約" },
      { day: 5, date: "2025-03-15", startTime: "11:00", endTime: "11:30", eventText: "予約" }
    ],
    5: [
      { day: 2, date: "2025-02-12", startTime: "13:30", endTime: "15:00", eventText: "テニスコート" }
    ],
    "卓球セット": [],
    "設備のみ": [],
    "備品のみ": []
  };


/**********************************************************
 * 週間カレンダー：モーダルダイアログ内の処理
 **********************************************************/
const calendarOverlay = document.getElementById('calendarDialogOverlay');
const closeCalendarBtn = document.getElementById('close-calendar');
closeCalendarBtn.onclick = function() {
  calendarOverlay.style.display = 'none';
  if (currentStep === 2) {
    const selectedDateTimeDiv = document.getElementById('selectedDateTime');
    if (selectedDateInfo.date && selectedDateInfo.startTime && selectedDateInfo.endTime) {
      selectedDateTimeDiv.textContent =
        `${selectedDateInfo.date} ${selectedDateInfo.startTime}～${selectedDateInfo.endTime}`;
    }
  }
};

const modal = document.getElementById("modal");
const modalContent = document.getElementById("modal-content");

let isDragging = false;
let dragStartCell = null;
let dragEndCell = null;
let currentCell = null;


function pad(num) { return num.toString().padStart(2, '0'); }
function timeToMinutes(timeStr) {
  const parts = timeStr.split(':').map(Number);
  return parts[0] * 60 + parts[1];
}
function getMonday(date) {
  const d = new Date(date);
  const day = d.getDay();
  const diff = (day === 0 ? -6 : 1 - day);
  d.setDate(d.getDate() + diff);
  d.setHours(0, 0, 0, 0);
  return d;
}
function updateHeader(monday) {
  const headerRow = document.querySelector("#calendar thead tr");
  const daysOfWeek = ["月","火","水","木","金","土","日"];
  for (let i = 0; i < 7; i++) {
    const d = new Date(monday);
    d.setDate(monday.getDate() + i);
    headerRow.cells[i + 1].innerHTML =
      `<span class="weekday">${daysOfWeek[i]}</span><br>
       <span class="date">${(d.getMonth() + 1)}/${d.getDate()}</span>`;
  }
}
function getRowIndex(cell) {
  const rows = Array.from(document.querySelectorAll("#calendar tbody tr"));
  return rows.indexOf(cell.parentNode);
}
function highlightRange(startCell, endCell) {
  let startIndex = getRowIndex(startCell);
  let endIndex = getRowIndex(endCell);
  if (startIndex > endIndex) {
    [startIndex, endIndex] = [endIndex, startIndex];
  }
  const day = startCell.dataset.day;
  const rows = document.querySelectorAll("#calendar tbody tr");
  rows.forEach((row, idx) => {
    if (idx >= startIndex && idx <= endIndex) {
      row.children[parseInt(day) + 1].classList.add("selected");
    }
  });
}
function removeSelectionHighlight(day, date) {
  const rows = document.querySelectorAll("#calendar tbody tr");
  rows.forEach((row) => {
    const cell = row.children[parseInt(day) + 1];
    if (cell && cell.dataset.date === date) {
      cell.classList.remove("selected");
    }
  });
}
function openNewEventDialogForRange(startCell, endCell) {
  let startMinutes = timeToMinutes(startCell.dataset.time);
  let endMinutes = timeToMinutes(endCell.dataset.time);
  if (startMinutes > endMinutes) {
    [startMinutes, endMinutes] = [endMinutes, startMinutes];
  }
  let eventEndMinutes = endMinutes + 15;
  if (eventEndMinutes > 24 * 60) eventEndMinutes = 24 * 60;
  const eventStart = pad(Math.floor(startMinutes / 60)) + ":" + pad(startMinutes % 60);
  const eventEnd = pad(Math.floor(eventEndMinutes / 60)) + ":" + pad(eventEndMinutes % 60);

  const modalHTML = `
    <h2>スケジュール登録</h2>
    <p><strong>開始:</strong> ${eventStart}</p>
    <p><strong>終了:</strong> ${eventEnd}</p>
    <label for="event-text">イベント内容:</label>
    <input type="text" id="event-text" placeholder="例）練習">
    <div id="modal-buttons">
      <button id="save-event">保存</button>
      <button id="cancel-event">キャンセル</button>
    </div>
  `;
  modalContent.innerHTML = modalHTML;
  modal.style.display = "flex";

  document.getElementById("save-event").addEventListener("click", function() {
    const eventText = document.getElementById("event-text").value.trim();
    if (!eventText) {
      alert("イベント内容を入力してください");
      return;
    }
    const dayIndex = startCell.dataset.day;
    const eventDate = startCell.dataset.date;
    registerEventForDay(dayIndex, eventDate, eventStart, eventEnd, eventText);
    selectedDateInfo.date = eventDate;
    selectedDateInfo.startTime = eventStart;
    selectedDateInfo.endTime = eventEnd;
    closeModal();
    if (currentStep === 2) {
        nextBtn.classList.remove('disabled');
    }
  });
  document.getElementById("cancel-event").addEventListener("click", closeModal);
}
function closeModal() {
  modal.style.display = "none";
}
function registerEventForDay(dayIndex, dateStr, startTime, endTime, eventText) {
  console.log(`[registerEventForDay] イベント処理開始: date=${dateStr}, startTime=${startTime}, endTime=${endTime}, eventText=${eventText}`);
  const tbody = document.getElementById("calendar-body");
  const rows = tbody.getElementsByTagName("tr");
  const startM = timeToMinutes(startTime);
  const endM = timeToMinutes(endTime);
  console.log(`[registerEventForDay] 開始分: ${startM}, 終了分: ${endM}`);
  for (let i = 0; i < rows.length; i++) {
    const cell = rows[i].children[parseInt(dayIndex) + 1];
    if (cell.dataset.date === dateStr) {
      const cellM = timeToMinutes(cell.dataset.time);
      if (cellM >= startM && cellM < endM) {
        console.log(`[registerEventForDay] マッチ: セル ${cell.dataset.date} ${cell.dataset.time} が条件に一致`);
        cell.classList.add("event");
        cell.dataset.eventText = eventText;
        cell.dataset.eventStart = startTime;
        cell.dataset.eventEnd = endTime;
        if (cellM === startM) {
          cell.textContent = eventText;
        }
      }
    }
  }
}
function removeEventFromColumn(dayIndex, dateStr, eventStart, eventEnd) {
  const tbody = document.getElementById("calendar-body");
  const rows = tbody.getElementsByTagName("tr");
  for (let i = 0; i < rows.length; i++) {
    const cell = rows[i].children[parseInt(dayIndex) + 1];
    if (cell.dataset.date === dateStr &&
        cell.dataset.eventStart === eventStart &&
        cell.dataset.eventEnd === eventEnd) {
      cell.classList.remove("event");
      cell.textContent = "";
      delete cell.dataset.eventText;
      delete cell.dataset.eventStart;
      delete cell.dataset.eventEnd;
    }
  }
}
function openEventDetailDialog(cell) {
  currentCell = cell;
  const eventText = cell.dataset.eventText;
  const eventStart = cell.dataset.eventStart;
  const eventEnd = cell.dataset.eventEnd;
  const modalHTML = `
    <h2>イベント詳細</h2>
    <p><strong>内容:</strong> ${eventText}</p>
    <p><strong>開始:</strong> ${eventStart}</p>
    <p><strong>終了:</strong> ${eventEnd}</p>
    <div id="modal-buttons">
      <button id="delete-event">削除</button>
      <button id="edit-event">編集</button>
      <button id="close-event">閉じる</button>
    </div>
  `;
  modalContent.innerHTML = modalHTML;
  modal.style.display = "flex";
  document.getElementById("delete-event").addEventListener("click", function() {
    if (confirm("このイベントを削除しますか？")) {
      removeEventFromColumn(cell.dataset.day, cell.dataset.date, eventStart, eventEnd);
      closeModal();
    }
  });
  document.getElementById("edit-event").addEventListener("click", function() {
    openEditEventDialog(cell);
  });
  document.getElementById("close-event").addEventListener("click", closeModal);
}
function openEditEventDialog(cell) {
  const eventText = cell.dataset.eventText;
  const eventStart = cell.dataset.eventStart;
  const eventEnd = cell.dataset.eventEnd;
  const modalHTML = `
    <h2>イベント編集</h2>
    <p><strong>開始:</strong> ${eventStart}</p>
    <label for="edit-event-text">イベント内容:</label>
    <input type="text" id="edit-event-text" value="${eventText}">
    <label for="edit-end-time">終了時間:</label>
    <input type="time" id="edit-end-time" value="${eventEnd}">
    <div id="modal-buttons">
      <button id="save-edit-event">保存</button>
      <button id="cancel-edit-event">キャンセル</button>
    </div>
  `;
  modalContent.innerHTML = modalHTML;
  modal.style.display = "flex";
  document.getElementById("save-edit-event").addEventListener("click", function() {
    const newEventText = document.getElementById("edit-event-text").value.trim();
    const newEndTime = document.getElementById("edit-end-time").value;
    if (!newEventText) {
      alert("イベント内容を入力してください");
      return;
    }
    if (timeToMinutes(newEndTime) <= timeToMinutes(eventStart)) {
      alert("終了時間は開始時間より後にしてください");
      return;
    }
    removeEventFromColumn(cell.dataset.day, cell.dataset.date, eventStart, eventEnd);
    registerEventForDay(cell.dataset.day, cell.dataset.date, eventStart, newEndTime, newEventText);
    selectedDateInfo.date = cell.dataset.date;
    selectedDateInfo.startTime = eventStart;
    selectedDateInfo.endTime = newEndTime;
    closeModal();
  });
  document.getElementById("cancel-edit-event").addEventListener("click", function() {
    openEventDetailDialog(cell);
  });
}
function generateCalendarRows(monday) {
  const calendarBody = document.getElementById("calendar-body");
  calendarBody.innerHTML = "";
  for (let hour = 8; hour < 24; hour++) {
    for (let quarter = 0; quarter < 4; quarter++) {
      const minute = quarter * 15;
      const timeStr = pad(hour) + ":" + pad(minute);
      const row = document.createElement("tr");
      const timeCell = document.createElement("td");
      timeCell.className = "time-label";
      timeCell.textContent = (minute === 0) ? timeStr : "";
      row.appendChild(timeCell);
      for (let day = 0; day < 7; day++) {
        const cell = document.createElement("td");
        cell.className = "cell";
        cell.dataset.day = day;
        cell.dataset.time = timeStr;
        const cellDate = new Date(monday);
        cellDate.setDate(monday.getDate() + day);
        const yyyy = cellDate.getFullYear();
        const mm = pad(cellDate.getMonth() + 1);
        const dd = pad(cellDate.getDate());
        cell.dataset.date = `${yyyy}-${mm}-${dd}`;
        cell.addEventListener("mousedown", (e) => {
          if (!cell.classList.contains("event")) {
            isDragging = true;
            dragStartCell = cell;
            dragEndCell = cell;
            cell.classList.add("selected");
            e.preventDefault();
          }
        });
        cell.addEventListener("mouseover", (e) => {
          if (isDragging) {
            if (cell.dataset.day === dragStartCell.dataset.day && cell.dataset.date === dragStartCell.dataset.date) {
              removeSelectionHighlight(cell.dataset.day, cell.dataset.date);
              dragEndCell = cell;
              highlightRange(dragStartCell, dragEndCell);
            }
          }
        });
        cell.addEventListener("mouseup", (e) => {
          if (isDragging) {
            isDragging = false;
            removeSelectionHighlight(dragStartCell.dataset.day, dragStartCell.dataset.date);
            openNewEventDialogForRange(dragStartCell, dragEndCell);
          }
        });
        // タッチ開始（touchstart）は mousedown に相当
        cell.addEventListener("touchstart", (e) => {
          // 複数タッチの場合、最初のタッチポイントを使用
          //const touch = e.touches[0];
          if (!cell.classList.contains("event")) {
            isDragging = true;
            dragStartCell = cell;
            dragEndCell = cell;
            cell.classList.add("selected");
            e.preventDefault(); // スクロールなどのデフォルト動作を抑制
          }
        });

        cell.addEventListener("touchmove", (e) => {
          if (isDragging) {
            const touch = e.touches[0];
            const currentCell = document.elementFromPoint(touch.clientX, touch.clientY);
            // currentCell が存在し、かつドラッグ開始セルと同じ列（data-day, data-date が同じ）であれば更新
            if (currentCell && currentCell.dataset &&
                currentCell.dataset.day === dragStartCell.dataset.day &&
                currentCell.dataset.date === dragStartCell.dataset.date) {
              removeSelectionHighlight(currentCell.dataset.day, currentCell.dataset.date);
              dragEndCell = currentCell;
              highlightRange(dragStartCell, dragEndCell);
            }
          }
          e.preventDefault();
        }, {passive: false});

        // タッチ終了（touchend）は mouseup に相当
        cell.addEventListener("touchend", (e) => {
          if (isDragging) {
            isDragging = false;
            removeSelectionHighlight(dragStartCell.dataset.day, dragStartCell.dataset.date);
            openNewEventDialogForRange(dragStartCell, dragEndCell);
          }
          e.preventDefault();
        });

        cell.addEventListener("click", (e) => {
          if (isDragging) return;
          if (cell.classList.contains("event")) {
            openEventDetailDialog(cell);
          } else {
            openNewEventDialogForRange(cell, cell);
          }
        });
        row.appendChild(cell);
      }
      calendarBody.appendChild(row);
    }
  }
}

function clearAllEquipmentReservations() {
  const cells = document.querySelectorAll("#calendar .cell");
  cells.forEach(cell => {
    cell.classList.remove("event");
    cell.textContent = "";
    delete cell.dataset.eventText;
    delete cell.dataset.eventStart;
    delete cell.dataset.eventEnd;
  });
}


const datePicker = document.getElementById("select-date");
const updateWeekBtn = document.getElementById("update-week");
const todayBtn = document.getElementById("today-button");
const equipmentSelect = document.getElementById("equipment-select");

let currentMonday = getMonday(new Date());
function initCalendar() {
  const today = new Date();
  datePicker.value = today.toISOString().split("T")[0];
  currentMonday = getMonday(today);
  updateHeader(currentMonday);
  generateCalendarRows(currentMonday);
}
updateWeekBtn.onclick = function() {
  if (datePicker.value) {
    const selectedDate = new Date(datePicker.value);
    currentMonday = getMonday(selectedDate);
    updateHeader(currentMonday);
    generateCalendarRows(currentMonday);
  }
};
todayBtn.onclick = function() {
  const now = new Date();
  datePicker.value = now.toISOString().split("T")[0];
  currentMonday = getMonday(now);
  updateHeader(currentMonday);
  generateCalendarRows(currentMonday);
};
equipmentSelect.onchange = function() {
  selectedEquipmentSet = this.value;
  loadEquipmentReservations(this.value);
};

function registerClosedForDay(dayIndex, dateStr, startTime, endTime, closedText) {
    const tbody = document.getElementById("calendar-body");
    const rows = tbody.getElementsByTagName("tr");
    const startM = timeToMinutes(startTime);
    const endM = timeToMinutes(endTime);
  
    for (let i = 0; i < rows.length; i++) {
      const cell = rows[i].children[parseInt(dayIndex) + 1];
      if (cell.dataset.date === dateStr) {
        const cellM = timeToMinutes(cell.dataset.time);
        if (cellM >= startM && cellM < endM) {
          // 「閉鎖」状態を示すクラスを付与
          cell.classList.add("closed");
          // 最初のセルにのみテキストを設定
          if (cellM === startM) {
            cell.textContent = closedText; 
          }
          // 背景色を変更し、クリックを無効化
          cell.style.backgroundColor = "#ddd"; 
          cell.style.pointerEvents = "none";
        }
      }
    }
}

function loadEquipmentReservations(equipmentId) {
    // 既存の予約イベントをクリア & 再描画
    clearAllEquipmentReservations();
  
    // 予約データを読み込んで反映
    const events = equipmentReservations[equipmentId] || [];
    events.forEach(ev => {
      console.log(`reservations day : ${ev.day}, date: ${ev.date}`);
      registerEventForDay(ev.day, ev.date, ev.startTime, ev.endTime, ev.eventText);
    });
  
    // ★ 閉鎖データも読み込んで反映
    const closedItems = facilityClosureSchedules[equipmentId] || [];
    closedItems.forEach(closed => {
      console.log(`closure day : ${closed.day}, date: ${closed.date}`);
      registerClosedForDay(closed.day, closed.date, closed.startTime, closed.endTime, closed.eventText);
    });
}


// 最後にカレンダーを初期化
initCalendar();
