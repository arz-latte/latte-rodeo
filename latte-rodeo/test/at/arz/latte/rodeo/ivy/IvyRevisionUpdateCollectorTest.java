package at.arz.latte.rodeo.ivy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.rodeo.engine.DependencyNode;
import at.arz.latte.rodeo.engine.DependencyTree;
import at.arz.latte.rodeo.engine.DependencyTreeMother;
import at.arz.latte.rodeo.engine.StringDependencyNode;

public class IvyRevisionUpdateCollectorTest {

	private DependencyTree<String> tree;
	private BaselineRepository<String> mockBaselineRepository;

	@Before
	public void createTree() {
		tree = DependencyTreeMother.createTree();
		mockBaselineRepository = new BaselineRepository<String>() {

			@Override
			public IvyRevision getBaseline(DependencyNode<String> node) {
				return new IvyRevision("1.2.3-asdf");
			}
		};
		// mockBaselineRepository = mock(BaselineRepository.class);
		// when(mockBaselineRepository.getBaseline((DependencyNode<String>)any())).thenReturn(new
		// IvyRevision("1.2.3-asdf"));
	}

	@Test
	public void hash() {
		IvyRevision rev1 = mockBaselineRepository.getBaseline(new StringDependencyNode("A"));
		int hash1 = System.identityHashCode(rev1);
		IvyRevision rev2 = mockBaselineRepository.getBaseline(new StringDependencyNode("A"));
		int hash2 = System.identityHashCode(rev2);
		Assert.assertNotEquals(hash1, hash2);
	}

	@Test
	public void single_minor_update() {
		Set<DependencyNode<String>> majorRevNodes = Collections.emptySet();
		Set<DependencyNode<String>> minorRevNodes = new HashSet<DependencyNode<String>>();
		minorRevNodes.add(new StringDependencyNode("E"));
		IvyRevisionUpdateCollector<String> collector = new IvyRevisionUpdateCollector<String>(	tree,
																								minorRevNodes,
																								majorRevNodes,
																								mockBaselineRepository);
		collector.simulate();
		Map<DependencyNode<String>, IvyRevision> changes = collector.getChanges();
		Assert.assertEquals(4, changes.size());
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("A")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("B")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("C")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("E")));
	}

	@Test
	public void multiple_updates() {
		Set<DependencyNode<String>> majorRevNodes = new HashSet<DependencyNode<String>>();
		Set<DependencyNode<String>> minorRevNodes = new HashSet<DependencyNode<String>>();
		minorRevNodes.add(new StringDependencyNode("E"));
		minorRevNodes.add(new StringDependencyNode("H"));
		majorRevNodes.add(new StringDependencyNode("C"));
		majorRevNodes.add(new StringDependencyNode("D"));
		IvyRevisionUpdateCollector<String> collector = new IvyRevisionUpdateCollector<String>(	tree,
																								minorRevNodes,
																								majorRevNodes,
																								mockBaselineRepository);
		collector.simulate();
		Map<DependencyNode<String>, IvyRevision> changes = collector.getChanges();
		Assert.assertEquals(7, changes.size());
		Assert.assertEquals(new IvyRevision("2.0.0"), changes.get(new StringDependencyNode("A")));
		Assert.assertEquals(new IvyRevision("2.0.0"), changes.get(new StringDependencyNode("B")));
		Assert.assertEquals(new IvyRevision("2.0.0"), changes.get(new StringDependencyNode("C")));
		Assert.assertEquals(new IvyRevision("2.0.0"), changes.get(new StringDependencyNode("D")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("E")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("F")));
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("H")));
	}

	@Test
	public void update_of_non_existing_node_will_be_ignored() {
		Set<DependencyNode<String>> majorRevNodes = new HashSet<DependencyNode<String>>();
		Set<DependencyNode<String>> minorRevNodes = new HashSet<DependencyNode<String>>();
		minorRevNodes.add(new StringDependencyNode("nonExistingMinor"));
		majorRevNodes.add(new StringDependencyNode("nonExistingMajor"));
		IvyRevisionUpdateCollector<String> collector = new IvyRevisionUpdateCollector<String>(	tree,
																								minorRevNodes,
																								majorRevNodes,
																								mockBaselineRepository);
		collector.simulate();
		Map<DependencyNode<String>, IvyRevision> changes = collector.getChanges();
		Assert.assertEquals(0, changes.size());
	}

	@Test
	public void major_update_of_root() {
		Set<DependencyNode<String>> majorRevNodes = new HashSet<DependencyNode<String>>();
		Set<DependencyNode<String>> minorRevNodes = new HashSet<DependencyNode<String>>();
		majorRevNodes.add(new StringDependencyNode("A"));
		IvyRevisionUpdateCollector<String> collector = new IvyRevisionUpdateCollector<String>(	tree,
																								minorRevNodes,
																								majorRevNodes,
																								mockBaselineRepository);
		collector.simulate();
		Map<DependencyNode<String>, IvyRevision> changes = collector.getChanges();
		Assert.assertEquals(1, changes.size());
		Assert.assertEquals(new IvyRevision("2.0.0"), changes.get(new StringDependencyNode("A")));
	}

	@Test
	public void minor_update_of_root() {
		Set<DependencyNode<String>> majorRevNodes = new HashSet<DependencyNode<String>>();
		Set<DependencyNode<String>> minorRevNodes = new HashSet<DependencyNode<String>>();
		minorRevNodes.add(new StringDependencyNode("A"));
		IvyRevisionUpdateCollector<String> collector = new IvyRevisionUpdateCollector<String>(	tree,
																								minorRevNodes,
																								majorRevNodes,
																								mockBaselineRepository);
		collector.simulate();
		Map<DependencyNode<String>, IvyRevision> changes = collector.getChanges();
		Assert.assertEquals(1, changes.size());
		Assert.assertEquals(new IvyRevision("1.3.0"), changes.get(new StringDependencyNode("A")));
	}
}
