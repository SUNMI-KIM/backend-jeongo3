package kr.kookmin.jeongo3.DISC;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.DISC.Dto.DISCRequestDto;
import kr.kookmin.jeongo3.DISC.Dto.DISCResponseDto;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DISCService {

    private final DISCRepository discRepository;

    public DISCResponseDto findDISC(User user) {
        DISC disc = discRepository.findByUser(user).orElseThrow(() -> new MyException(ErrorCode.DISC_NOT_FOUND));
        long countByDISC = discRepository.countByDiscCode(disc.getDiscCode());
        return DISCResponseDto.builder().sameDISCNum(countByDISC).discCode(disc.getDiscCode()).build();
    }

    @Transactional
    public DISCResponseDto saveDISC(DISCRequestDto discRequestDto, User user) {
        discRepository.deleteByUser(user);
        DISCCode discCode = elicitDISC(discRequestDto);
        DISC disc = DISC.builder().user(user).discCode(discCode).build();
        discRepository.save(disc);
        long countByDISC = discRepository.countByDiscCode(disc.getDiscCode());
        return DISCResponseDto.builder().sameDISCNum(countByDISC).discCode(discCode).build();
    }

    public long countDISC() {
        return discRepository.count();
    }

    private DISCCode elicitDISC(DISCRequestDto discRequestDto) {
        String DISCResult = "";

        Map<String, Integer> DISCmap = makeMap(discRequestDto);
        for (int i = 0; i < 4; i++) {
            Map.Entry<String, Integer> maxEntry = Collections.max(DISCmap.entrySet(), comparator);
            if (maxEntry.getValue() < 35) {
                break;
            }
            DISCResult = DISCResult + maxEntry.getKey();
            DISCmap.remove(maxEntry.getKey());
        }
        if (DISCResult.isEmpty() || DISCResult.length() > 3) {
            throw new MyException(ErrorCode.BAD_REQUEST_DISC);
        }
        return DISCCode.valueOf(DISCResult);
    }

    Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    };

    private Map<String, Integer> makeMap(DISCRequestDto discRequestDto) {
        Map<String, Integer> map = new HashMap<>();
        map.put("D", discRequestDto.getDScore());
        map.put("I", discRequestDto.getIScore());
        map.put("S", discRequestDto.getSScore());
        map.put("C", discRequestDto.getCScore());
        return map;
    }


}
