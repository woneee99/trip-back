package com.example.tripback.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventDto {

    @Getter
    @NoArgsConstructor
    public static class saveRequestDto {
        @NotNull(message = "시작일은 Null 일 수 없습니다.")
        @Temporal(TemporalType.DATE)
        @DateTimeFormat(style = "yyyy-MM-dd")
        private LocalDate startDate;

        @NotNull(message = "종료일은 Null 일 수 없습니다.")
        @Temporal(TemporalType.DATE)
        @DateTimeFormat(style = "yyyy-MM-dd")
        private LocalDate endDate;

        @Temporal(TemporalType.TIME)
        @DateTimeFormat(style = "hh:mm")
        private LocalTime startTime;
        @Temporal(TemporalType.TIME)
        @DateTimeFormat(style = "hh:mm")
        private LocalTime endTime;

        @NotNull(message = "제목은 Null 일 수 없습니다.")
        private String title;

        @NotNull
        private Long teamId;

        @Builder
        public saveRequestDto(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String title) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.title = title;
        }

        public Events toEntity(){
            return Events.builder().startDate(startDate).endDate(endDate).startTime(startTime).endTime(endTime).title(title).build();
        }
    }

    @Getter
    public static class eventList{
        private Long eventId;

        @DateTimeFormat(style = "yyyy-MM-dd")
        private LocalDate startDate;

        @DateTimeFormat(style = "yyyy-MM-dd")
        private LocalDate endDate;

        @DateTimeFormat(style = "hh:mm")
        private LocalTime startTime;

        @DateTimeFormat(style = "hh:mm")
        private LocalTime endTime;

        private String title;

        public eventList(Events events) {
            this.eventId = events.getEventId();
            this.startDate = events.getStartDate();
            this.endDate = events.getEndDate();
            this.startTime = events.getStartTime();
            this.endTime = events.getEndTime();
            this.title = events.getTitle();
        }
    }
    @Getter
    public static class responseList{
        private List<eventList> calendarList;

        public responseList(List<Events> calendarList) {
            this.calendarList = calendarList.stream()
                    .map(eventList::new)
                    .collect(Collectors.toList());
        }
    }
}
