package org.vaadin.addons.md_stepper.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List that will work in a circular manner.
 * <p>
 * It will automatically wrap around the edges and return the according element.
 *
 * @param <E>
 *     The type of the elements of the list
 */
public class CircularList<E> extends ArrayList<E> {

  /**
   * Create a new circular list from the given list.
   *
   * @param list
   *     The list to use to create the circular list
   * @param offset
   *     The element at the specified offset position will be the first element in the circular
   *     list. All preceeding elements will be appended to the end
   * @param <E>
   *     The type of the elements of the list
   */
  public static <E> CircularList<E> from(List<E> list, int offset) {
    Objects.requireNonNull(list, "List may not be null");

    CircularList<E> circularList = new CircularList<>();

    if (offset < 0) {
      circularList.addAll(list);
    } else {
      circularList.addAll(list.subList(offset, list.size()));
      circularList.addAll(list.subList(0, offset));
    }

    return circularList;
  }

  @Override
  public E get(int index) {
    int listSize = super.size();
    int indexToGet = index % listSize;

    indexToGet = (indexToGet < 0) ? indexToGet + listSize : indexToGet;
    return super.get(indexToGet);
  }
}
