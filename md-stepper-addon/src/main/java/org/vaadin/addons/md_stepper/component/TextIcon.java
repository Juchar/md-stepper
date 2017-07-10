package org.vaadin.addons.md_stepper.component;

import com.vaadin.server.FontIcon;
import com.vaadin.server.Resource;

import java.util.Objects;

/**
 * A font icon that will show a given {@link String}.
 */
public final class TextIcon implements FontIcon {

  private final String text;

  /**
   * Create a new icon showing the given text.
   * <p>
   * <b>ATTENTION</b><br>
   * Please be aware that using this with {@link com.vaadin.ui.Component#setIcon(Resource)} will
   * only show the first character of the text, whereas using {@link #getHtml()} will provide html
   * containing the complete text.
   *
   * @param text
   *     The text to show
   */
  public TextIcon(String text) {
    Objects.requireNonNull(text, "Text may not be null");
    this.text = text;
  }

  @Override
  public String getFontFamily() {
    return "inherit";
  }

  @Override
  public int getCodepoint() {
    return text.codePointAt(0);
  }

  @Override
  public String getHtml() {
    return "<span class=\"v-icon v-label-bold\">" + text + "</span>";
  }

  @Override
  public String getMIMEType() {
    throw new UnsupportedOperationException(FontIcon.class.getSimpleName()
                                            + " should not be used where a MIME type is needed.");
  }
}
