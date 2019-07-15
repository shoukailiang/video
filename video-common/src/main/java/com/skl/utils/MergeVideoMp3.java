package com.skl.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

  private String ffmpegEXE;

  public MergeVideoMp3(String ffmpegEXE) {
    super();
    this.ffmpegEXE = ffmpegEXE;
  }

  public void convertor(String videoInputPath, String mp3InputPath,
                        double seconds, String videoOutputPath) throws Exception {
    // -t 时间
//		ffmpeg.exe -i haha.mp4 -i bgm.mp3 -t 7 -y 新的视频.mp4 测了一下发现失效

//    1、先去除源视频的音频轨
//    ffmpeg -i input.mp4 -c:v copy -an -y input-no-audio.mp4
//    2、再把新的音频混进去
//    ffmpeg -i input-no-audio.mp4 -i input.mp3 -t 7  -c copy  -y output.mp4


    List<String> command = new ArrayList<>();
    List<String> command2 = new ArrayList<>();
    // 去除声音
    command.add(ffmpegEXE);
    command.add("-i");
    command.add(videoInputPath);

    command.add("-c:v");
    command.add("copy");
    command.add("-an");
    command.add("-y");
    command.add("C:\\Users\\liang\\Desktop\\input-no-audio.mp4");

    nextExc(command);


    // 加入声音
    command2.add(ffmpegEXE);
    command2.add("-i");
    command2.add("C:\\Users\\liang\\Desktop\\input-no-audio.mp4");
    command2.add("-i");
    command2.add(mp3InputPath);

    command2.add("-t");
    command2.add(String.valueOf(seconds));
    command2.add("-c");
    command2.add("copy");

    command2.add("-y");
    command2.add(videoOutputPath);

    nextExc(command2);
  }

  public void nextExc(List<String> command) throws Exception {

    for (String c : command) {
      System.out.print(c + " ");
    }
    System.out.println("");


    ProcessBuilder builder = new ProcessBuilder(command);
    Process process = builder.start();


    InputStream errorStream = process.getErrorStream();
    InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
    BufferedReader br = new BufferedReader(inputStreamReader);

    String line = "";
    while ((line = br.readLine()) != null) {
    }

    if (br != null) {
      br.close();
    }
    if (inputStreamReader != null) {
      inputStreamReader.close();
    }
    if (errorStream != null) {
      errorStream.close();
    }
  }

  public static void main(String[] args) {
    MergeVideoMp3 ffmpeg = new MergeVideoMp3("C:\\ffmpeg\\bin\\ffmpeg.exe");
    try {
      ffmpeg.convertor("C:\\Users\\liang\\Desktop\\WeChat_20190711203253.mp4", "C:\\video\\bgm\\hello.mp3", 27, "C:\\Users\\liang\\Desktop\\我.mp4");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
