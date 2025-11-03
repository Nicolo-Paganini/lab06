package it.unibo.generics.graph.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N> {
    
    private final Map<N, Set<N>> nodeGraph = new LinkedHashMap<>();

    @Override
    public void addNode(final N node){
        nodeGraph.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void addEdge(final N source,final N target){
        if (nodeGraph.containsKey(source) && nodeGraph.containsKey(target) && source != null && target != null) {
            nodeGraph.get(source).add(target);
        }
    }

    @Override
    public Set<N> nodeSet(){
        return nodeGraph.keySet();
    }

    @Override
    public Set<N> linkedNodes(final N node){
        return nodeGraph.get(node);
    }

    @Override
    public List<N> getPath(final N source,final N target){
         if (this.nodeGraph.containsKey(source) && this.nodeGraph.containsKey(target)){
            if (source.equals(target)){
                return Collections.emptyList();
            }
            final Queue<N> nodeToVisit = new LinkedList<>();
            nodeToVisit.add(source);
            final Map<N, N> solutionPath = new HashMap<>();
            solutionPath.put(source, null);
            final Set<N> visitedNode = new HashSet<>();
            visitedNode.add(source);
            while (!nodeToVisit.isEmpty()){
                final N currentNode = nodeToVisit.remove();
                if (currentNode.equals(target)){
                    final List<N> path = new LinkedList<>();
                    N currentStep = target;
                    while (currentStep != null) {
                        path.add(0, currentStep); 
                        currentStep = solutionPath.get(currentStep); 
                    }
                    return path;
                }
                for (N n : this.nodeGraph.get(currentNode)) {
                    if (!visitedNode.contains(n)){
                        nodeToVisit.add(n);
                        visitedNode.add(n);
                        solutionPath.put(n, currentNode);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
