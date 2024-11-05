function formatEventDateRange(fromDate, toDate) {
    // 日付文字列から "T" を除去し、分割する
    const formattedFromDate = fromDate.replace("T", " ");
    const formattedToDate = toDate.replace("T", " ");

    // 日付部分を比較して、同じであれば "to" の日付を省略する
    const fromDatePart = formattedFromDate.split(" ")[0];
    const toDatePart = formattedToDate.split(" ")[0];
    const fromTimePart = formattedFromDate.split(" ")[1];
    const toTimePart = formattedToDate.split(" ")[1];

    if (fromDatePart === toDatePart) {
        return `${fromDatePart} ${fromTimePart} 〜 ${toTimePart}`;
    } else {
        return `${fromDatePart} ${fromTimePart} 〜 ${toDatePart} ${toTimePart}`;
    }
}