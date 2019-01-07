package com.ssplugins.advent.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node {
    
    private List<Node> children;
    private List<Byte> metadata;
    
    public Node(List<Node> children) {
        this.children = children;
    }
    
    public static Node fromInput(String input) {
        List<Byte> data = Stream.of(input.split(" ")).map(Byte::parseByte).collect(Collectors.toList());
        return toNode(data);
    }
    
    private static Node toNode(List<Byte> data) {
        List<Node> children = readChildren(data);
        Node node = new Node(children);
        node.setMetadata(new ArrayList<>(data.subList(data.size() - data.get(1), data.size())));
        return node;
    }
    
    private static List<Node> readChildren(List<Byte> data) {
        int childs = data.get(0);
        List<Node> children = new ArrayList<>(childs);
        int index = 2;
        int end = data.size() - data.get(1);
        while (index < end) {
            int e = findNodeEnd(data, index);
            children.add(toNode(data.subList(index, e)));
            index = e;
        }
        return children;
    }
    
    private static int findNodeEnd(List<Byte> data, int start) {
        byte read = 1; // 0:child, 1:meta, 2:meta-read
        List<Byte> childs = new ArrayList<>();
        List<Byte> meta = new ArrayList<>();
        childs.add(data.get(start));
        int index = start + 1;
        while (index < data.size()) {
            if (read == 3) {
                break;
            }
            if (read == 1) {
                meta.add(data.get(index));
                if (childs.get(childs.size() - 1) > 0) {
                    read = 0;
                }
                else {
                    read = 2;
                }
                index++;
            }
            else if (read == 0) {
                childs.add(data.get(index));
                read = 1;
                index++;
            }
            else {
                index += meta.get(meta.size() - 1);
                meta.remove(meta.size() - 1);
                childs.remove(childs.size() - 1);
                if (childs.size() == 0){
                    read = 3;
                    continue;
                }
                byte r = childs.remove(childs.size() - 1);
                r--;
                childs.add(r);
                if (r > 0) {
                    read = 0;
                }
                else if (r == 0) {
                    read = 2;
                }
            }
        }
        return index;
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    public List<Byte> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(List<Byte> metadata) {
        this.metadata = metadata;
    }
    
}
