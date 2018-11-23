/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BST.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月21日
 */
package org.demo.sort.data;

import java.util.List;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="BST.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    protected Node root;
    
    protected class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        /*以该节点为根的子树节点总数*/
        int N;
        //红黑树中使用
        boolean color;
        
        public Node(Key key ,Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
        
    }
    
    @Override
    public int size() {
        return size(root);
    }

    private int size(BST<Key, Value>.Node root) {
        if(root != null) {
            return root.N;
        }
        return 0;
    }
    
    protected void recalculateSize(Node root) {
        root.N = size(root.left) + size(root.right) + 1;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private BST<Key, Value>.Node put(BST<Key, Value>.Node root, Key key, Value val) {
        if (root == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            root.val = val;
        } else if (cmp < 0) {
            root.left = put(root.left, key, val);
        } else {
            root.right = put(root.right, key, val);
        }
        recalculateSize(root);
        return root;
    }
    
    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(BST<Key, Value>.Node root, Key key) {
        if (root == null || key == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root.val;
        } else if (cmp < 0){
            return get(root.left, key);
        } else {
            return get(root.right, key);
        }
    }
    
    //小于等于key的最大值
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }
    
    private BST<Key, Value>.Node floor(BST<Key, Value>.Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }
        if (cmp < 0) {
            return floor(root.left, key);
        }
        Node t = floor(root.right, key);
        return t != null ? t : root;
    }

    @Override
    public Key min() {
        Node keyNode = min(root);
        if(keyNode != null) {
            return keyNode.key;
        }
        return null;
    }
    private Node min(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        return min(root.left);
        
    }

    @Override
    public Key max() {
        Node maxNode = max(root);
        if (maxNode == null) {
            return null;
        }
        return maxNode.key;
    }
    
    private Node max(Node root) {
        if (root == null) {
            return null;
        }
        if (root.right == null) {
            return root;
        }
        return max(root.right);
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }
    
    private int rank(Key key, BST<Key, Value>.Node root) {
        if (root == null) {
            return 0;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return size(root.left);
        } else if (cmp < 0){
            return rank(key, root.left);
        } else {
            return 1 + size(root.left) + rank(key, root.right);
        }
    }

    @Override
    public List<Key> keys(Key l, Key h) {
        return null;
    }

    
    public void deleteMin() {
        root = deleteMin(root);
    }
    
    public Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        recalculateSize(x);
        return x;
    }
    
    
    public void delete(Key key) {
        
    }
}
