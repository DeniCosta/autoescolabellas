document.addEventListener("DOMContentLoaded", function () {
    const dp = new DayPilot.Calendar("dp", {
        locale: "pt-br",
        viewType: "Week",
        headerDateFormat: "ddd d/M/yyyy",
        businessEndsHour: 20,
        dayBeginsHour: 9,
        width: 50,
        hourWidth: 50,
        timeRangeSelectedHandling: "Enabled",
        cellDuration: 60,
        timeHeaderCellDuration : 30,
        onTimeRangeSelected: async (args) => {
            const modal = await DayPilot.Modal.prompt("Create a new event:", "Event 1");
            const dp = args.control;
            dp.clearSelection();
            if (modal.canceled) {
                return;
            }
            dp.events.add({
                start: args.start,
                end: args.end,
                id: DayPilot.guid(),
                text: modal.result
            });
        },
        eventDeleteHandling: "Update",
        onEventDeleted: (args) => {
            console.log("Event deleted: " + args.e.text());
        },
        eventMoveHandling: "Update",
        onEventMoved: (args) => {
            console.log("Event moved: " + args.e.text());
        },
        eventResizeHandling: "Update",
        onEventResized: (args) => {
            console.log("Event resized: " + args.e.text());
        },
        eventClickHandling: "Select",
        onEventSelected: (args) => {
            args.control.message("Event selected: " + args.e.text());
        },
        eventHoverHandling: "Disabled",
    });

    const nav = new DayPilot.Navigator("nav", {
        width: 50,
        showMonths: 1,
        skipMonths: 1,
        selectMode: "week",
        onTimeRangeSelected: (args) => {
            dp.startDate = args.day;
            dp.update();
        }
    });
    dp.init();
    nav.init();
});