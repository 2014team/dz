package com.artcweb.test.colorLine;
class WeaveConfig {
  public int lines;
  public int jump_lines;
  public int remove_intensity;
  public int draw_color;

  public WeaveConfig(int lines, int jump_lines, int remove_intensity, int draw_color) {
    this.lines = lines;
    this.jump_lines = jump_lines;
    this.remove_intensity = remove_intensity;
    this.draw_color = draw_color;
  }
}