package leamon.erp.ui.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class GenericModelWithSnp <T extends List<E>, E> {
	private T ob;
	private List<Integer> sno;
}
