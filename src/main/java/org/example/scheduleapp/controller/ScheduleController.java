package org.example.scheduleapp.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.dto.ScheduleRequest;
import org.example.scheduleapp.dto.ScheduleResponse;
import org.example.scheduleapp.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @RequestBody ScheduleRequest scheduleRequest
    ){
        return ResponseEntity.ok(scheduleService.save(scheduleRequest));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> getSchedules(){
        return ResponseEntity.ok(scheduleService.findSchedules());
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getSchedule(
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest scheduleRequest //변경된 요청값을 받을 곳? 받은? 받는 코드?
    ){
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, scheduleRequest));//새로 변경된 값을 서비스로 보내자.
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest request
    ){
        scheduleService.deleteSchedule(scheduleId, request);
    }
}
