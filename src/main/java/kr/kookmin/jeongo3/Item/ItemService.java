package kr.kookmin.jeongo3.Item;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Aws.S3Service;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Item.Dto.RequestItemDto;
import kr.kookmin.jeongo3.Item.Dto.RequestItemModifyDto;
import kr.kookmin.jeongo3.Item.Dto.ResponseItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kr.kookmin.jeongo3.Exception.ErrorCode.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final S3Service s3Service;

    public void saveItem(RequestItemDto requestItemDto) {
        String fileName = UUID.randomUUID() + requestItemDto.getImage().getOriginalFilename();
        try {
            s3Service.upload(requestItemDto.getImage(), fileName);
        } catch (IOException e) {

        }
        Item item = requestItemDto.toEntity(requestItemDto.getName(),
                                            fileName,
                                            requestItemDto.getPrice());
        itemRepository.save(item);
    }

    public ResponseItemDto findItem(String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));
        item.setImage(s3Service.getPresignedURL(item.getImage()));
        return new ResponseItemDto(item);
    }

    public List<ResponseItemDto> findAllItem() {
        List<ResponseItemDto> items = itemRepository.findAll()
                .stream()
                .map(item -> {
                    ResponseItemDto dto = new ResponseItemDto();
                    dto.setId(item.getId());
                    dto.setName(item.getName());
                    dto.setPrice(item.getPrice());
                    dto.setImage(s3Service.getPresignedURL(item.getImage()));
                    return dto;
                })
                .collect(Collectors.toList());
        return items;
    }

    public void deleteItem(String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));
        s3Service.delete(item.getImage());
        itemRepository.delete(item);
    }

    @Transactional
    public void ModifyItem(RequestItemModifyDto requestItemModifyDto) {
        Item item = itemRepository.findById(requestItemModifyDto.getItemId()).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));
        item.setName(requestItemModifyDto.getName());
        item.setPrice(requestItemModifyDto.getPrice());

        s3Service.delete(item.getImage());
        String fileName = UUID.randomUUID() + requestItemModifyDto.getImage().getOriginalFilename();
        try {
            s3Service.upload(requestItemModifyDto.getImage(), fileName);
        } catch (IOException e) {

        }
        item.setImage(fileName);
    }
}
