package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    public void clear(){
        itemRepository.clearStore();
    }

    @Test
    public void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    public void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    public void updateItem() {
        //given
        Item item = new Item("itemA", 10000, 20);
        Item savedItem = itemRepository.save(item);

        //when
        Item changeItem = new Item("brandNew", 20000, 40);
        itemRepository.update(savedItem.getId(), changeItem);

        //then
        assertThat(savedItem.getItemName()).isEqualTo(changeItem.getItemName());
        assertThat(savedItem.getPrice()).isEqualTo(changeItem.getPrice());
        assertThat(savedItem.getQuantity()).isEqualTo(changeItem.getQuantity());
    }
}
