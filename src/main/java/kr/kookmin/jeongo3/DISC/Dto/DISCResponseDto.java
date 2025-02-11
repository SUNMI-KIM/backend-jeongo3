package kr.kookmin.jeongo3.DISC.Dto;


import kr.kookmin.jeongo3.DISC.DISCCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DISCResponseDto {

    private long sameDISCNum;
    private DISCCode discCode;

    @Builder
    public DISCResponseDto(long sameDISCNum, DISCCode discCode) {
        this.sameDISCNum = sameDISCNum;
        this.discCode = discCode;
    }
}
