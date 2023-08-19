package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
//@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    //생성자가 한개면 생략가능 @Autowired, 롬복의 @RequiredArgsConstructor를사용하면 생성자자체도 생략가능
    @Autowired 
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes){
        //GetParam으로 각각 받아도되지만 ModelAttribute로 한번에 저장
        Item savedItem = itemRepository.save(item);
        //@ModelAttribute는 model객체의 addAttribute도 자동으로 수행해줌! (클래스이름을 변수작명규칙으로 Item->item)
        //model.addAttribute("item", item);

        redirectAttributes.addAttribute("itemId", savedItem.getId()); //리턴시 값사용가능
        redirectAttributes.addAttribute("status", true); //쿼리파라미터형식으로 넘어감
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") long id, Model model){
        Item foundItem = itemRepository.findById(id);
        model.addAttribute("item", foundItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") long id, @ModelAttribute Item item){
        itemRepository.update(id, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct //테스트용 데이터 추가
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 15));
    }
}
