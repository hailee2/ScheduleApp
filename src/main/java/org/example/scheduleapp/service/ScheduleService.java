package org.example.scheduleapp.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapp.dto.ScheduleRequest;
import org.example.scheduleapp.dto.ScheduleResponse;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponse save(ScheduleRequest scheduleRequest){
        Schedule schedule = new Schedule(
                scheduleRequest.getTitle(),
                scheduleRequest.getAuthor(),
                scheduleRequest.getContent(),
                scheduleRequest.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getAuthor(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getAuthor(),
                        schedule.getContent(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponse findSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleRequest request){ //request변수는 컨트롤러에서 받은 요청값(비밀번호포함)이 담겨있는 변수
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow( //schedule변수는 원래 저장되어있던 비밀번호가 담겨있는 변수!
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );
        if(request.getPassword() != schedule.getPassword()){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        schedule.updateSchedule(request.getTitle(), request.getContent(), request.getPassword());
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, ScheduleRequest request){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );
        if(!request.getPassword().equals(schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
//        boolean b = scheduleRepository.existsById(scheduleId);
//        if(!b) {
//            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
//        }
        scheduleRepository.deleteById(scheduleId);
    }

}
